package q3df.mil;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import q3df.mil.config.ModelMapperConfig;
import q3df.mil.mapper.UserMapper;
import q3df.mil.repository.DialogRepository;
import q3df.mil.service.UserService;
import q3df.mil.service.impl.DialogServiceImpl;
import q3df.mil.service.impl.FriendServiceImpl;
import q3df.mil.service.impl.MessageServiceImpl;
import q3df.mil.service.impl.SubscriberServiceImpl;



@SpringBootApplication
@Import(ModelMapperConfig.class)
public class MilSclNtwrkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MilSclNtwrkApplication.class, args);
	}


	@Bean
	public CommandLineRunner process(UserService userService,
									 UserMapper userMapper,
									 DialogRepository dialogRepository,
									 DialogServiceImpl dialogService,
									 MessageServiceImpl messageService,
									 FriendServiceImpl friendService,
									 SubscriberServiceImpl subscriberService
									 ) {
		return a -> {
//			UserDto user = userRepository.findById(1L);
//			UserDto userDto = userMapper.toDto(user);
//			User user1 = userMapper.fromDto(userDto);
//			System.out.println(user);
//			System.out.println(user1);

//			List<DialogDto> dialogsByUserId = dialogService.findDialogsByUserId(1L);
//			System.out.println(dialogsByUserId);


//			List<MessageDto> messagesByDialogId = messageService.findMessagesByDialogId(1L);
//
//			System.out.println(messagesByDialogId);

//			System.out.println(messageService.findMessagesByDialogId(1L));
//			System.out.println(friendService.findUserFriends(1L));
//			System.out.println(subscriberService.findUserSubscribers(1L));
			System.out.println("Hello");

		};
	}


}