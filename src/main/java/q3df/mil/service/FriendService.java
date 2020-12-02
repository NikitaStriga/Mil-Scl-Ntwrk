package q3df.mil.service;

import q3df.mil.dto.friends_subs_dto.FriendDto;
import q3df.mil.entities.users_roles.User;

import java.util.List;

public interface FriendService {


    List<FriendDto> findUserFriends(Long id);
}
