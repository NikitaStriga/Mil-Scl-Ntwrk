package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.message.Message;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findMessagesByDialogIdOrderByCreatedDesc(Long id);

    @Query(value = "select dialog_id from messages where from_who=?1 and to_who=?2 limit 1",
            nativeQuery = true)
    Long checkForDialogIdIfItsExistInMessages(Long fromWhoId, Long toWhoId);

}
