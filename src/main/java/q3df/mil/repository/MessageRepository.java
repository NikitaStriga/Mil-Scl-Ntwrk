package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import q3df.mil.entities.message.Message;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findMessagesByDialogId(Long id);


//    @Modifying
//    @Query(value = "delete from messages where from_who=?1 or to_who=?2",
//            nativeQuery=true)


    int deleteByFromWhoIdOrToWhoId(Long idFromWho,Long idToWho);

}
