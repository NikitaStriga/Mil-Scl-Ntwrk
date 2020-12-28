package q3df.mil.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfig {

    @Bean
    public CacheManager cacheManager() {
        final String [] cacheNames = {"justForExampleCacheForUsersWithIdLessThan50"};
        //create Manager
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(cacheNames);
        //set properties
        cacheManager.setCaffeine(cacheProperties());
        return cacheManager;
    }

    //set properties
    public Caffeine<Object, Object> cacheProperties() {
        return Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(50)
//                .expireAfterAccess(10, TimeUnit.SECONDS) //lifetime after last access
                .expireAfterWrite(10, TimeUnit.SECONDS) //lifetime after recording
                .weakKeys(); //tells caffeine to create weak references on keys
//                .recordStats();  //record stats about cache
    }

}
