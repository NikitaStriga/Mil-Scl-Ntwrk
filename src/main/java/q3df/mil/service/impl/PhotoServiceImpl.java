package q3df.mil.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.photo.p.PhotoDto;
import q3df.mil.dto.photo.p.PhotoSaveDto;
import q3df.mil.dto.photo.p.PhotoUpdateDto;
import q3df.mil.entities.photo.Photo;
import q3df.mil.exception.PhotoNotFoundException;
import q3df.mil.mapper.photo.p.PhotoMapper;
import q3df.mil.mapper.photo.p.PhotoSaveMapper;
import q3df.mil.repository.PhotoRepository;
import q3df.mil.service.PhotoService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;
    private final PhotoSaveMapper photoSaveMapper;

    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoMapper photoMapper, PhotoSaveMapper photoSaveMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
        this.photoSaveMapper = photoSaveMapper;
    }


    @Override
    @Transactional
    public PhotoDto findById(Long id) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new PhotoNotFoundException("Photo with id " + id + " doesn't exist!"));
        return photoMapper.toDto(photo);
    }

    @Override
    @Transactional
    public PhotoDto savePhoto(PhotoSaveDto photoSaveDto) {
        Photo photo = photoSaveMapper.fromDto(photoSaveDto);
        Photo savedPhoto = photoRepository.save(photo);
        return photoMapper.toDto(savedPhoto);
    }

    @Override
    @Transactional
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

    @Override
    public void deletePhotoById(Long id) {
        try{
            photoRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new PhotoNotFoundException("Photo with id " + id + " doesn't exist!");
        }
    }
}
