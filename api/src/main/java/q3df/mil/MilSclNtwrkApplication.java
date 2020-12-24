package q3df.mil;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import q3df.mil.config.CaffeineConfig;
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
import q3df.mil.repository.custom.impl.CustomRepositoryImpl;
import q3df.mil.service.UserService;
import q3df.mil.service.impl.DialogServiceImpl;
import q3df.mil.service.impl.FriendServiceImpl;
import q3df.mil.service.impl.MessageServiceImpl;
import q3df.mil.service.impl.SubscriberServiceImpl;
import q3df.mil.service.impl.UserServiceImpl;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableSwagger2
@EnableCaching
@Import({ModelMapperConfig.class,
		MessageSourceAndLocalValidatorConfig.class,
		SwaggerConfig.class,
		PasswordEncoderConfiguration.class,
		MailConfig.class,
		CaffeineConfig.class
})
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
									 TextCommentRepository textCommentRepository,
									 CustomRepositoryImpl customRepository,
									 UserServiceImpl userServiceimpl
									 ) {
		return a -> {

		};
	}

}