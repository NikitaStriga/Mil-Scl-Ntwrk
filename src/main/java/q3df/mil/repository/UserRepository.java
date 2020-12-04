package q3df.mil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.user.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select * from users u join user_friends uf on u.id=uf.user_id where u.id=?1",
            nativeQuery=true)
    List<User> findUserFriends(Long id);


    @Query(value = "select * from users u join user_friends uf on u.id=uf.user_id where u.id=?1",
            nativeQuery=true)
    List<User> findUserSubscribers(Long id);


    User getOne(Long id);

    List<User> findUsersByEmailOrLogin(String email,String login);


    void deleteById(Long id);


}
