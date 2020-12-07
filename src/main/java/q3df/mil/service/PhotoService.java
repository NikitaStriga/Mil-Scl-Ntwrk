package q3df.mil.service;


import q3df.mil.dto.photo.PhotoDto;

public interface PhotoService {

//    PhotoDto findById(Long id);

    PhotoDto savePhoto(PhotoDto photoDto);

    PhotoDto updatePhoto(PhotoDto photoDto);

    void deletePhotoById(Long id);

}
