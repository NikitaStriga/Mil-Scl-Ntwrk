package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.dialog.Dialog;

import java.util.List;

@Repository
public interface DialogRepository extends JpaRepository<Dialog,Long> {



    @Query(value= "select * from dialogs d join  user_dialogs  ud on d.id=ud.dialog_id where ud.user_id=?1 order by created DESC",nativeQuery=true)
    List<Dialog> findDialogsByUserId(Long id);


    void deleteById(Long id);

}
