package q3df.mil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import q3df.mil.amazon.AmazonSender;
import q3df.mil.dto.photo.p.PhotoDto;
import q3df.mil.dto.photo.p.PhotoSaveDto;
import q3df.mil.dto.photo.p.PhotoUpdateDto;
import q3df.mil.entities.photo.Photo;
import q3df.mil.entities.user.User;
import q3df.mil.exception.PhotoNotFoundException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.photo.p.PhotoMapper;
import q3df.mil.mapper.photo.p.PhotoSaveMapper;
import q3df.mil.repository.PhotoRepository;
import q3df.mil.service.PhotoService;

import javax.persistence.EntityNotFoundException;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;
    private final PhotoSaveMapper photoSaveMapper;
    private final AmazonSender amazonSender;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoMapper photoMapper, PhotoSaveMapper photoSaveMapper, AmazonSender amazonSender) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
        this.photoSaveMapper = photoSaveMapper;
        this.amazonSender = amazonSender;
    }


    /**
     * find photo by id
     * @param id photo id
     * @return founded photo
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PhotoDto findById(Long id) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new PhotoNotFoundException("Photo with id " + id + " doesn't exist!"));
        return photoMapper.toDto(photo);
    }


    /**
     * save photo to amazon and  then add the record to the db
     * @param photoSaveDto photo 'description'
     * @param multipartFile file
     * @return saved photo
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public String savePhoto(PhotoSaveDto photoSaveDto, MultipartFile multipartFile) {
        Photo photo = photoSaveMapper.fromDto(photoSaveDto);

        //send photo to Amazon
        String urlOfFile = amazonSender.sendToAmazon(multipartFile, photoSaveDto.getUserId());

        //set the resulting path
        photo.setPath(urlOfFile);

        photoRepository.save(photo);
        return urlOfFile;
    }


    /**
     * update photo
     * if users want to make the main photo,
     * need  to remove the boolean flag from the old main photo
     * @param photoUpdateDto photo
     * @return updated photo
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public PhotoDto updatePhoto(PhotoUpdateDto photoUpdateDto) {
        Photo photo;
        try{
            photo = photoRepository.getOne(photoUpdateDto.getId());
        }catch (EntityNotFoundException ex){
            throw  new PhotoNotFoundException("Photo with id " + photoUpdateDto.getId() + " doesn't exist!");
        }
        photo.setDescription(photoUpdateDto.getDescription());

        //if user want to change main photo 1st we false previous main photo and then define true to this
        if (photoUpdateDto.getMainPhoto()) {
            photoRepository.setMainPhotoToFalse();
            photo.setMainPhoto(true);
        }

        return photoMapper.toDto(photo);
    }


    /**
     * delete photo from db and amazon
     * @param id photo id
     */
    @Override
    public void deletePhotoById(Long id) {
        Photo photo;
        try {
            photo = photoRepository.getOne(id);
        } catch (EntityNotFoundException ex) {
            throw new UserNotFoundException("Photo with id " + id + " doesn't exist!");
        }

        //deleting a record from the db
        photoRepository.deleteById(photo.getId());

        //deleting a file from amazon
        amazonSender.deleteFromAmazon(photo.getPath());

    }


}
