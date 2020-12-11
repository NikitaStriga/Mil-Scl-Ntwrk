package q3df.mil.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.photo.pl.PhotoLikeDto;
import q3df.mil.dto.photo.pl.PhotoLikeSaveDto;
import q3df.mil.entities.photo.PhotoLike;
import q3df.mil.exception.PhotoLikeNotFoundException;
import q3df.mil.mapper.photo.pl.PhotoLikeMapper;
import q3df.mil.mapper.photo.pl.PhotoLikeSaveMapper;
import q3df.mil.repository.PhotoLikeRepository;
import q3df.mil.service.PhotoLikeService;

@Service
public class PhotoLikeServiceImpl implements PhotoLikeService {

    private final PhotoLikeRepository photoLikeRepository;
    private final PhotoLikeMapper photoLikeMapper;
    private final PhotoLikeSaveMapper photoLikeSaveMapper;



    public PhotoLikeServiceImpl(PhotoLikeRepository photoLikeRepository, PhotoLikeMapper photoLikeMapper, PhotoLikeSaveMapper photoLikeSaveMapper) {
        this.photoLikeRepository = photoLikeRepository;
        this.photoLikeMapper = photoLikeMapper;
        this.photoLikeSaveMapper = photoLikeSaveMapper;
    }


    @Override
    public PhotoLikeDto savePhotoLike(PhotoLikeSaveDto photoLikeSaveDto) {
        PhotoLike photoLike = photoLikeSaveMapper.fromDto(photoLikeSaveDto);
        PhotoLike savedPhotoLike = photoLikeRepository.save(photoLike);
        return photoLikeMapper.toDto(savedPhotoLike);
    }



    @Override
    public void deletePhotoLikeById(Long id) {
        try{
            photoLikeRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new PhotoLikeNotFoundException("Photo like with id " + id + " doesn't exist!");
        }
    }
}
