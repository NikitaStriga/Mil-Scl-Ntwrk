package q3df.mil.service;


import org.springframework.web.multipart.MultipartFile;
import q3df.mil.dto.photo.p.PhotoDto;
import q3df.mil.dto.photo.p.PhotoSaveDto;
import q3df.mil.dto.photo.p.PhotoUpdateDto;

public interface PhotoService {

    PhotoDto findById(Long id);

    String savePhoto(PhotoSaveDto photoSaveDto, MultipartFile multipartFile);

    PhotoDto updatePhoto(PhotoUpdateDto photoUpdateDto);

    void deletePhotoById(Long id);

}
