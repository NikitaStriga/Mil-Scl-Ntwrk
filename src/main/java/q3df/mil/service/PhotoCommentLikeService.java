package q3df.mil.service;

import q3df.mil.dto.photo.pcl.PhotoCommentLikeDto;
import q3df.mil.dto.photo.pcl.PhotoCommentLikeSaveDto;

public interface PhotoCommentLikeService {

    PhotoCommentLikeDto savePhotoCommentLike(PhotoCommentLikeSaveDto photoCommentLikeSaveDto);

    void deleteCommentLikeById(Long id);
}
