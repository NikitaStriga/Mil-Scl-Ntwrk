package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.text_entities.TextCommentLike;

@Repository
public interface TextCommentLikeRepository extends JpaRepository<TextCommentLike,Long> {
}
