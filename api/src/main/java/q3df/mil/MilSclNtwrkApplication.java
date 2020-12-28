package q3df.mil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import q3df.mil.config.CaffeineConfig;
import q3df.mil.config.MessageSourceAndLocalValidatorConfig;
import q3df.mil.config.ModelMapperConfig;
import q3df.mil.config.PasswordEncoderConfiguration;
import q3df.mil.config.SwaggerConfig;
import q3df.mil.mail.MailConfig;
import q3df.mil.security.configuration.WebSecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableSwagger2
@EnableCaching
@Import({ModelMapperConfig.class,
		MessageSourceAndLocalValidatorConfig.class,
		SwaggerConfig.class,
		PasswordEncoderConfiguration.class,
		WebSecurityConfiguration.class,
		MailConfig.class,
		CaffeineConfig.class,
})
public class MilSclNtwrkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MilSclNtwrkApplication.class, args);
	}

}