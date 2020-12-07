package q3df.mil.service;

import q3df.mil.dto.photo.PhotoCommentDto;


public interface PhotoCommentService {

//    PhotoCommentDto findById(Long id);

    PhotoCommentDto savePhotoComment(PhotoCommentDto photoCommentDto);

    PhotoCommentDto updatePhotoComment(PhotoCommentDto photoCommentDto);

    void deletePhotoCommentById(Long id);
}
