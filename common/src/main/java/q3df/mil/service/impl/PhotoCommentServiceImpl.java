package q3df.mil.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.photo.pc.PhotoCommentDto;
import q3df.mil.dto.photo.pc.PhotoCommentSaveDto;
import q3df.mil.dto.photo.pc.PhotoCommentUpdateDto;
import q3df.mil.entities.photo.PhotoComment;
import q3df.mil.exception.PhotoCommentNotFoundException;
import q3df.mil.mapper.photo.pc.PhotoCommentMapper;
import q3df.mil.mapper.photo.pc.PhotoCommentSaveMapper;
import q3df.mil.repository.PhotoCommentRepository;
import q3df.mil.service.PhotoCommentService;

import javax.persistence.EntityNotFoundException;

import static q3df.mil.exception.ExceptionConstants.PHOTO_COMMENT_NF;

@Service
public class PhotoCommentServiceImpl implements PhotoCommentService {


    private final PhotoCommentRepository photoCommentRepository;
    private final PhotoCommentMapper photoCommentMapper;
    private final PhotoCommentSaveMapper photoCommentSaveMapper;

    public PhotoCommentServiceImpl(PhotoCommentRepository photoCommentRepository,
                                   PhotoCommentMapper photoCommentMapper,
                                   PhotoCommentSaveMapper photoCommentSaveMapper) {
        this.photoCommentRepository = photoCommentRepository;
        this.photoCommentMapper = photoCommentMapper;
        this.photoCommentSaveMapper = photoCommentSaveMapper;
    }


    /**
     * save photo comment
     * @param photoCommentSaveDto new photo comment
     * @return saved photo comment
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public PhotoCommentDto savePhotoComment(PhotoCommentSaveDto photoCommentSaveDto) {
        PhotoComment photoComment = photoCommentSaveMapper.fromDto(photoCommentSaveDto);
        PhotoComment savedPhotoComment = photoCommentRepository.save(photoComment);
        return photoCommentMapper.toDto(savedPhotoComment);
    }


    /**
     * update photo comment
     * @param photoCommentUpdateDto photo comment
     * @return updated photo comment
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public PhotoCommentDto updatePhotoComment(PhotoCommentUpdateDto photoCommentUpdateDto) {
        PhotoComment photoComment =
                photoCommentRepository
                .findById(photoCommentUpdateDto.getId())
                .orElseThrow( () -> new PhotoCommentNotFoundException(PHOTO_COMMENT_NF + photoCommentUpdateDto.getId()));
        photoComment.setComment(photoCommentUpdateDto.getComment());
        return photoCommentMapper.toDto(photoComment);
    }


    /**
     * delete photo comment
     * @param id photo comment id
     */
    @Override
    public void deletePhotoCommentById(Long id) {
        try{
        photoCommentRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new PhotoCommentNotFoundException(PHOTO_COMMENT_NF + id);
        }
    }

}
