package q3df.mil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import q3df.mil.dto.friend.FriendDto;
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
        return userRepository.findUserFriends(id).stream().map(friendMapper::toDto).collect(Collectors.toList());
    }
}
