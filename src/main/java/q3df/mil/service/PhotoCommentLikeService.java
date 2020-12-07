package q3df.mil.service;

import q3df.mil.dto.photo.PhotoCommentLikeDto;

public interface PhotoCommentLikeService {

    PhotoCommentLikeDto savePhotoCommentLike(PhotoCommentLikeDto photoCommentLikeDto);

    void deleteCommentLikeById(Long id);
}
