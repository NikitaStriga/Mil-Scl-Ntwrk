package q3df.mil.service.impl;

import org.springframework.stereotype.Service;
import q3df.mil.dto.photo.PhotoCommentLikeDto;
import q3df.mil.entities.photo.PhotoCommentLike;
import q3df.mil.mapper.photo.PhotoCommentLikeMapper;
import q3df.mil.repository.PhotoCommentLikeRepository;
import q3df.mil.service.PhotoCommentLikeService;

@Service
public class PhotoCommentLikeServiceImpl implements PhotoCommentLikeService {

    private final PhotoCommentLikeRepository photoCommentLikeRepository;
    private final PhotoCommentLikeMapper photoCommentLikeMapper;

    public PhotoCommentLikeServiceImpl(PhotoCommentLikeRepository photoCommentLikeRepository,
                                       PhotoCommentLikeMapper photoCommentLikeMapper) {
        this.photoCommentLikeRepository = photoCommentLikeRepository;
        this.photoCommentLikeMapper = photoCommentLikeMapper;
    }



    @Override
    public PhotoCommentLikeDto savePhotoCommentLike(PhotoCommentLikeDto photoCommentLikeDto) {
        PhotoCommentLike photoCommentLike = photoCommentLikeMapper.fromDto(photoCommentLikeDto);
        PhotoCommentLike savedPhotoCommentLike = photoCommentLikeRepository.save(photoCommentLike);
        return photoCommentLikeMapper.toDto(savedPhotoCommentLike);
    }

    @Override
    public void deleteCommentLikeById(Long id) {
        photoCommentLikeRepository.deleteById(id);
    }
}
