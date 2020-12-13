package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.photo.Photo;



@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {


    @Modifying
    @Query(value = "update photos set main_photo=false  where main_photo=true", nativeQuery = true)
    void setMainPhotoToFalse();

}
