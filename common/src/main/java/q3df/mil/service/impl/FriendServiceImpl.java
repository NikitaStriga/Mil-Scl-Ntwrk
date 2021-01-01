package q3df.mil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import q3df.mil.dto.friend.FriendDto;
import q3df.mil.exception.CustomException;
import q3df.mil.mapper.friend.FriendMapper;
import q3df.mil.repository.UserRepository;
import q3df.mil.service.FriendService;

import java.util.List;
import java.util.stream.Collectors;

import static q3df.mil.exception.ExceptionConstants.FRIEND_NF;

@Service
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final FriendMapper friendMapper;

    @Autowired
    public FriendServiceImpl(UserRepository userRepository, FriendMapper friendMapper) {
        this.userRepository = userRepository;
        this.friendMapper = friendMapper;
    }


    /**
     * find user friend by user id
     * @param id user id
     * @return list of user friends
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<FriendDto> findUserFriends(Long id) {
        return userRepository
                .findUserFriends(id)
                .stream()
                .map(friendMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * add new friend to user
     * 1. also in this method, it first checks if the added user is in the subscribers of another user,
     * if not, then an exception will be thrown
     * 2. checked whether the user already exists in friends, if its true an exception will be thrown
     * @param userId user who accepted another user to friend list
     * @param friendId friend id
     * @return number of rows (its already 1 if all is ok)
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public int addFriendToUser(Long userId, Long friendId) {
        //if we need add friend to user, first of all nee to check if friend is always exist in friend list
        // then -> need to check friend in subscriber list if its exist delete from subs and add to friends
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


    /**
     * delete friend from user
     * if a user is removed from friends, he will be added to subscribers
     * @param userId user who want to delete another from friend list
     * @param friendId friend id
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteFriendFromUser(Long userId, Long friendId) {
        //delete from friend list
        int i = userRepository.deleteFromFriends(userId, friendId);
        if (i==0) {
            throw new CustomException(FRIEND_NF + friendId);
        }
        //if we delete friend we need to add him in a subscriber list
        userRepository.addSubscriber(userId,friendId);
    }

}
