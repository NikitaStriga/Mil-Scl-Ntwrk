package q3df.mil;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import q3df.mil.config.MessageSourceAndLocalValidatorConfig;
import q3df.mil.config.ModelMapperConfig;
import q3df.mil.config.PasswordEncoderConfiguration;
import q3df.mil.config.SwaggerConfig;
import q3df.mil.mail.MailConfig;
import q3df.mil.mapper.user.UserMapper;
import q3df.mil.repository.DialogRepository;
import q3df.mil.repository.TextCommentRepository;
import q3df.mil.repository.TextRepository;
import q3df.mil.repository.UserRepository;
import q3df.mil.service.UserService;
import q3df.mil.service.impl.DialogServiceImpl;
import q3df.mil.service.impl.FriendServiceImpl;
import q3df.mil.service.impl.MessageServiceImpl;
import q3df.mil.service.impl.SubscriberServiceImpl;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableSwagger2
@Import({ModelMapperConfig.class,
		MessageSourceAndLocalValidatorConfig.class,
		SwaggerConfig.class,
		PasswordEncoderConfiguration.class,
		MailConfig.class})
public class MilSclNtwrkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MilSclNtwrkApplication.class, args);
	}


	@Bean
	public CommandLineRunner process(UserRepository userRepository,
									 UserService userService,
									 UserMapper userMapper,
									 DialogRepository dialogRepository,
									 DialogServiceImpl dialogService,
									 MessageServiceImpl messageService,
									 FriendServiceImpl friendService,
									 SubscriberServiceImpl subscriberService,
									 TextRepository textRepository,
									 TextCommentRepository textCommentRepository
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
//			System.out.println("Hello");
//			System.out.println(userRepository.findAll(PageRequest.of(1, 3)).getSize());
//			System.out.println(userRepository.findByFirstNameLike("%u%", PageRequest.of(1, 3)).size());
//			System.out.println(userRepository.findByBirthdayBetween(LocalDate.parse("2010-01-01"), LocalDate.parse("2020-01-01"), PageRequest.of(0, 30, Sort.by(Sort.Direction.DESC,"birthday"))).size());
//			System.out.println(userRepository.findByLastNameIgnoreCase("vivians").size());
//			System.out.println(userRepository.findByFirstNameLikeIgnoreCase("%duArdo%").size());
//			friendService.addFriendToUser(1L,2L);
//			userRepository.deleteFromSubs(1L,2L);
//			dialogService.deleteById(1L);
//			userRepository.deleteById(1L);
//			textRepository.deleteById(1L);
//			textCommentRepository.deleteById(1L);
		};
	}


}