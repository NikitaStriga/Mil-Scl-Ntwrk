package q3df.mil.service;

import q3df.mil.dto.friend.FriendDto;

import java.util.List;

public interface FriendService {

    List<FriendDto> findUserFriends(Long id);

    int addFriendToUser(Long userId, Long friendId);

    void deleteFriendFromUser(Long userId, Long friendId);

}
