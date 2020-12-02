package q3df.mil.service.impl;


import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import q3df.mil.dto.friends_subs_dto.SubscriberDto;
import q3df.mil.mapper.friend_sub_mappers.SubscriberMapper;
import q3df.mil.repository.UserRepository;
import q3df.mil.service.SubscriberService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SubscriberServiceImpl implements SubscriberService {
    Logger logger= LogManager.getLogger(SubscriberServiceImpl.class);
    private final UserRepository userRepository;
    private final SubscriberMapper subscriberMapper;

    public SubscriberServiceImpl(UserRepository userRepository, SubscriberMapper subscriberMapper) {
        this.userRepository = userRepository;
        this.subscriberMapper = subscriberMapper;
    }

    @Override
    @Transactional
    public List<SubscriberDto> findUserSubscribers(Long id) {
        return userRepository.findUserSubscribers(id).stream().map(subscriberMapper::toDto).collect(Collectors.toList());
    }
}
