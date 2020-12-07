package q3df.mil.service.impl;

import org.springframework.stereotype.Service;
import q3df.mil.dto.photo.PhotoLikeDto;
import q3df.mil.entities.photo.PhotoLike;
import q3df.mil.mapper.photo.PhotoLikeMapper;
import q3df.mil.repository.PhotoLikeRepository;
import q3df.mil.service.PhotoLikeService;

@Service
public class PhotoLikeServiceImpl implements PhotoLikeService {

    private final PhotoLikeRepository photoLikeRepository;
    private final PhotoLikeMapper photoLikeMapper;



    public PhotoLikeServiceImpl(PhotoLikeRepository photoLikeRepository, PhotoLikeMapper photoLikeMapper) {
        this.photoLikeRepository = photoLikeRepository;
        this.photoLikeMapper = photoLikeMapper;
    }


    @Override
    public PhotoLikeDto savePhotoLike(PhotoLikeDto photoLikeDto) {
        PhotoLike photoLike = photoLikeMapper.fromDto(photoLikeDto);
        PhotoLike savedPhotoLike = photoLikeRepository.save(photoLike);
        return photoLikeMapper.toDto(savedPhotoLike);
    }



    @Override
    public void deletePhotoLikeById(Long id) {
        photoLikeRepository.deleteById(id);
    }
}
