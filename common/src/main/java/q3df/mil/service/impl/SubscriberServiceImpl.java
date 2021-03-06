package q3df.mil.service.impl;


import org.springframework.stereotype.Service;
import q3df.mil.dto.subscriber.SubscriberDto;
import q3df.mil.exception.CustomException;
import q3df.mil.mapper.subscriber.SubscriberMapper;
import q3df.mil.repository.UserRepository;
import q3df.mil.service.SubscriberService;

import java.util.List;
import java.util.stream.Collectors;

import static q3df.mil.exception.ExceptionConstants.SUBSCRIBER_NF;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final UserRepository userRepository;
    private final SubscriberMapper subscriberMapper;

    public SubscriberServiceImpl(UserRepository userRepository, SubscriberMapper subscriberMapper) {
        this.userRepository = userRepository;
        this.subscriberMapper = subscriberMapper;
    }

    @Override
    public List<SubscriberDto> findUserSubscribers(Long id) {
        return userRepository
                .findUserSubscribers(id)
                .stream()
                .map(subscriberMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * add subscriber
     * at the same time it is checked whether this user is already a friend or subscriber
     * @param userId who are following
     * @param subscriberId who subscribes
     * @return added rows (always 1)
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public int addSubscriber(Long userId, Long subscriberId) {
        Long userIdFromCheck1 = userRepository.checkForFriend(userId, subscriberId);
        if (userIdFromCheck1!=null){
            throw new CustomException("Cant add subscriber with id "
                    + subscriberId + " to user with id " +
                    userId + " cause he almost in friends of user!");
        }
        Long userIdFromCheck2 = userRepository.checkForSubscriber(userId, subscriberId);
        if(userIdFromCheck2!=null){
            throw new CustomException("Cant add subscriber with id "
                    + subscriberId + " to user with id " +
                    userId + " cause he almost in subscribers of user!");
        }
        userRepository.addSubscriber(userId,subscriberId);
        return userRepository.addSubscriber(userId,subscriberId);
    }


    /**
     * delete from subscribers
     * @param userId owner of subscriber :)
     * @param subscriberId who wants to be deleted from subscribers
     */
    @Override
    public void deleteSubscriber(Long userId, Long subscriberId) {
        int i = userRepository.deleteFromSubs(userId, subscriberId);
        if(i==0){
            throw new CustomException(SUBSCRIBER_NF+ subscriberId);
        }
    }

}
