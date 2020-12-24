package q3df.mil.service;


import q3df.mil.dto.photo.p.PhotoDto;
import q3df.mil.dto.photo.p.PhotoSaveDto;
import q3df.mil.dto.photo.p.PhotoUpdateDto;

public interface PhotoService {

    PhotoDto findById(Long id);

    PhotoDto savePhoto(PhotoSaveDto photoSaveDto);

    PhotoDto updatePhoto(PhotoUpdateDto photoUpdateDto);

    void deletePhotoById(Long id);

}
