package q3df.mil.service.impl;

import org.springframework.stereotype.Service;
import q3df.mil.dto.photo.PhotoDto;
import q3df.mil.entities.photo.Photo;
import q3df.mil.mapper.photo.PhotoMapper;
import q3df.mil.repository.PhotoRepository;
import q3df.mil.service.PhotoService;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;

    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }


    @Override
    public PhotoDto savePhoto(PhotoDto photoDto) {
        Photo photo = photoMapper.fromDto(photoDto);
        Photo savedPhoto = photoRepository.save(photo);
        return photoMapper.toDto(savedPhoto);
    }

    @Override
    public PhotoDto updatePhoto(PhotoDto photoDto) {
        Photo photo = photoMapper.fromDto(photoDto);
        Photo savedPhoto = photoRepository.save(photo);
        return photoMapper.toDto(savedPhoto);
    }

    @Override
    public void deletePhotoById(Long id) {
        photoRepository.deleteById(id);
    }
}
