package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.photo.PhotoLike;


@Repository
public interface PhotoLikeRepository extends JpaRepository<PhotoLike,Long> {
}
