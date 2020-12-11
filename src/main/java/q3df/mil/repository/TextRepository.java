package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.text.Text;

import java.util.List;

@Repository
public interface TextRepository extends JpaRepository<Text,Long> {


    @Query(value = "select * from text where user_id=:id order by created desc", nativeQuery=true)
    List<Text> findTextByUserIdWithDescOrder(Long id);



}
