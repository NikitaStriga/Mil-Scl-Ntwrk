package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.photo.PhotoCommentLike;



@Repository
public interface PhotoCommentLikeRepository extends JpaRepository<PhotoCommentLike,Long> {
}
