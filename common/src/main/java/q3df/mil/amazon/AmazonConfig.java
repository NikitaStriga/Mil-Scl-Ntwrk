package q3df.mil.amazon;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "amazon")
public class AmazonConfig {

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String region;

    private String searchPattern;


}
