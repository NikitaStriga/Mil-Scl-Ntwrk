package q3df.mil.service.impl;

import org.springframework.stereotype.Service;
import q3df.mil.dto.photo.PhotoCommentDto;
import q3df.mil.entities.photo.PhotoComment;
import q3df.mil.mapper.photo.PhotoCommentMapper;
import q3df.mil.repository.PhotoCommentRepository;
import q3df.mil.service.PhotoCommentService;

@Service
public class PhotoCommentServiceImpl implements PhotoCommentService {


    private final PhotoCommentRepository photoCommentRepository;
    private final PhotoCommentMapper photoCommentMapper;

    public PhotoCommentServiceImpl(PhotoCommentRepository photoCommentRepository, PhotoCommentMapper photoCommentMapper) {
        this.photoCommentRepository = photoCommentRepository;
        this.photoCommentMapper = photoCommentMapper;
    }

    @Override
    public PhotoCommentDto savePhotoComment(PhotoCommentDto photoCommentDto) {
        PhotoComment photoComment = photoCommentMapper.fromDto(photoCommentDto);
        PhotoComment savedPhotoComment = photoCommentRepository.save(photoComment);
        return photoCommentMapper.toDto(savedPhotoComment);
    }


    @Override
    public PhotoCommentDto updatePhotoComment(PhotoCommentDto photoCommentDto) {
        PhotoComment photoComment = photoCommentMapper.fromDto(photoCommentDto);
        PhotoComment savedPhotoComment = photoCommentRepository.save(photoComment);
        return photoCommentMapper.toDto(savedPhotoComment);
    }


    @Override
    public void deletePhotoCommentById(Long id) {
        photoCommentRepository.deleteById(id);
    }
}
