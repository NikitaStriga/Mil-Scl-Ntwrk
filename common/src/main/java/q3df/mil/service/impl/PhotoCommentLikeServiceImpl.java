package q3df.mil.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.photo.pcl.PhotoCommentLikeDto;
import q3df.mil.dto.photo.pcl.PhotoCommentLikeSaveDto;
import q3df.mil.entities.photo.PhotoCommentLike;
import q3df.mil.exception.PhotoCommentLikeNotFoundException;
import q3df.mil.mapper.photo.pcl.PhotoCommentLikeMapper;
import q3df.mil.mapper.photo.pcl.PhotoCommentLikeSaveMapper;
import q3df.mil.repository.PhotoCommentLikeRepository;
import q3df.mil.service.PhotoCommentLikeService;

@Service
public class PhotoCommentLikeServiceImpl implements PhotoCommentLikeService {

    private final PhotoCommentLikeRepository photoCommentLikeRepository;
    private final PhotoCommentLikeMapper photoCommentLikeMapper;
    private final PhotoCommentLikeSaveMapper photoCommentLikeSaveMapper;

    public PhotoCommentLikeServiceImpl(PhotoCommentLikeRepository photoCommentLikeRepository,
                                       PhotoCommentLikeMapper photoCommentLikeMapper, PhotoCommentLikeSaveMapper photoCommentLikeSaveMapper) {
        this.photoCommentLikeRepository = photoCommentLikeRepository;
        this.photoCommentLikeMapper = photoCommentLikeMapper;
        this.photoCommentLikeSaveMapper = photoCommentLikeSaveMapper;
    }



    @Override
    @org.springframework.transaction.annotation.Transactional
    public PhotoCommentLikeDto savePhotoCommentLike(PhotoCommentLikeSaveDto photoCommentLikeSaveDto) {
        PhotoCommentLike photoCommentLike = photoCommentLikeSaveMapper.fromDto(photoCommentLikeSaveDto);
        PhotoCommentLike savedPhotoCommentLike = photoCommentLikeRepository.save(photoCommentLike);
        return photoCommentLikeMapper.toDto(savedPhotoCommentLike);
    }


    @Override
    public void deleteCommentLikeById(Long id) {
        try{
            photoCommentLikeRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new PhotoCommentLikeNotFoundException("Photo comment like with id " + id + " doesn't exist!");
        }
    }
}