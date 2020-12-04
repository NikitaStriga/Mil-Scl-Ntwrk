package q3df.mil.service;

import q3df.mil.dto.subscriber.SubscriberDto;

import java.util.List;

public interface SubscriberService {


    List<SubscriberDto> findUserSubscribers(Long id);
}
