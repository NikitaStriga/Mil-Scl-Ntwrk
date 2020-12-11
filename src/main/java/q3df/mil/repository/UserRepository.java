package q3df.mil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import q3df.mil.entities.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByLogin(String login);

    List<User> findUsersByEmailOrLogin(String email,String login);



    //pageable + sort + search

    //findAll
    Page<User> findAll(Pageable page);

    //by birthday between ...
    List<User> findByBirthdayBetween(LocalDate before, LocalDate after, Pageable page);

    //by country + city
    List<User> findByCountryIgnoreCaseAndCityIgnoreCase(String country, String city, Pageable page);

    //by firstName and Surname
    List<User> findByFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase(String name, String surname,Pageable page);




    //logic for subs and friends

    //find user friends
    @Query(value = "select * from users u join user_friends uf on u.id=uf.user_id where u.id=?1",
            nativeQuery=true)
    List<User> findUserFriends(Long id);

    //add friend
    @Query(value = "insert into user_friends (user_id, friend_id) VALUES (?1,?2)",
            nativeQuery=true)
    int addFriend(Long userId, Long subscriberId);

    //check for friend
    @Query(value = "select u.user_id from user_friends u where user_id=?1 and friend_id=?2",
            nativeQuery=true)
    Long checkForFriend(Long userId, Long friendId);

    //delete friend
    @Query(value = "delete from user_friends where user_id=?1 and friend_id=?2",
            nativeQuery=true)
    int deleteFromFriends(Long userId, Long subId);


    //find user subscribers
    @Query(value = "select * from users u join user_friends uf on u.id=uf.user_id where u.id=?1",
            nativeQuery=true)
    List<User> findUserSubscribers(Long id);

    //add subscriber
    @Query(value = "insert into user_subscribers (user_id, subscriber_id) VALUES (?1,?2)",
            nativeQuery=true)
    int addSubscriber(Long userId, Long subscriberId);

    //check for subscriber
    @Query(value = "select u.user_id from user_subscribers u where user_id=?1 and subscriber_id=?2",
            nativeQuery=true)
    Long checkForSubscriber(Long userId, Long friendId);

    //delete from subs
    @Query(value = "delete from user_subscribers where user_id=?1 and subscriber_id=?2",
            nativeQuery=true)
    int deleteFromSubs(Long userId, Long subId);




}
