package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.messages_dialogs.Message;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findMessagesByDialogId(Long id);

}
