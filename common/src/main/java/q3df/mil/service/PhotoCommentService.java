package q3df.mil.service;

import q3df.mil.dto.photo.pc.PhotoCommentDto;
import q3df.mil.dto.photo.pc.PhotoCommentSaveDto;
import q3df.mil.dto.photo.pc.PhotoCommentUpdateDto;


public interface PhotoCommentService {

    PhotoCommentDto savePhotoComment(PhotoCommentSaveDto photoCommentSaveDto);

    PhotoCommentDto updatePhotoComment(PhotoCommentUpdateDto photoCommentUpdateDto);

    void deletePhotoCommentById(Long id);
}
