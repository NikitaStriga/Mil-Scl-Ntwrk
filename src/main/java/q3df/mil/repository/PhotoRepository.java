package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.photo.Photo;



@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {
}
