package q3df.mil.service;

import q3df.mil.dto.photo.PhotoLikeDto;

public interface PhotoLikeService {

    PhotoLikeDto savePhotoLike(PhotoLikeDto photoLikeDto);

    void deletePhotoLikeById(Long id);
}
