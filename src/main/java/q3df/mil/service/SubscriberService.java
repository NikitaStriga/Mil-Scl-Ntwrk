package q3df.mil.service;

import q3df.mil.dto.friends_subs_dto.SubscriberDto;

import java.util.List;

public interface SubscriberService {


    List<SubscriberDto> findUserSubscribers(Long id);
}
