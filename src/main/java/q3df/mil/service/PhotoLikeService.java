package q3df.mil.service;

import q3df.mil.dto.photo.pl.PhotoLikeDto;
import q3df.mil.dto.photo.pl.PhotoLikeSaveDto;

public interface PhotoLikeService {

    PhotoLikeDto savePhotoLike(PhotoLikeSaveDto photoLikeSaveDto);

    void deletePhotoLikeById(Long id);
}
