package q3df.mil.service.impl;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import q3df.mil.dto.friend.FriendDto;
import q3df.mil.exception.CustomException;
import q3df.mil.mapper.friend.FriendMapper;
import q3df.mil.repository.UserRepository;
import q3df.mil.service.FriendService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final FriendMapper friendMapper;

    @Autowired
    public FriendServiceImpl(UserRepository userRepository, FriendMapper friendMapper) {
        this.userRepository = userRepository;
        this.friendMapper = friendMapper;
    }



    @Transactional
    @Override
    public List<FriendDto> findUserFriends(Long id) {
        return userRepository
                .findUserFriends(id)
                .stream()
                .map(friendMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public int addFriendToUser(Long userId, Long friendId) {
        //if we need add friend to user, first of all nee to check if friend is always exist in friend list
        // then -> need to check friend in subscriber list if its exist delete from subs and add to friends
        System.out.println(friendId);
        Long checkInFriendList = userRepository.checkForFriend(userId,friendId);
        if(checkInFriendList!=null){
            throw new CustomException("Friend with id " + friendId + " is always exist in friend list of user");
        }
        Long checkInSubList = userRepository.checkForSubscriber(userId, friendId);
        if(checkInSubList==null){
            throw new CustomException("Can't add friend to user cause we cant find him in subscribers!");
        }else {
            userRepository.deleteFromSubs(userId,friendId);
        }
            return userRepository.addFriend(userId,friendId);

    }


    @Override
    public void deleteFriendFromUser(Long userId, Long friendId) {
        //delete from friend list
        int i = userRepository.deleteFromFriends(userId, friendId);
        if (i==0) {
            throw new CustomException("Cant find friend with id " + friendId + " in user list!");
        }
        //if we delete friend we need to add him in a subscriber list
        userRepository.addSubscriber(userId,friendId);
    }

}
