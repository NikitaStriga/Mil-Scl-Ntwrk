package q3df.mil.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfig {

    @Bean(value = "justForExampleCacheForUsersWithIdLessThan50")
    public CacheManager cacheManagerForJustForExampleCacheForUsersWithIdLessThan50() {
        final String [] cacheNames = {"justForExampleCacheForUsersWithIdLessThan50"};
        //create Manager
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(cacheNames);
        //set properties
        cacheManager.setCaffeine(cacheJustForExampleCacheForUsersWithIdLessThan50Props());
        return cacheManager;
    }

    //set properties
    public Caffeine<Object, Object> cacheJustForExampleCacheForUsersWithIdLessThan50Props() {
        return Caffeine.newBuilder()
                .initialCapacity(10)
                .maximumSize(50)
//                .expireAfterAccess(10, TimeUnit.SECONDS) //lifetime after last access
                .expireAfterWrite(10, TimeUnit.SECONDS) //lifetime after recording
                .weakKeys(); //tells caffeine to create weak references on keys
//                .recordStats();  //record stats about cache
    }


    @Bean(value = "verTokCache")
    public CacheManager cacheManagerForVerTokCache() {
        final String [] cacheNames = {"verTokCache"};
        //create Manager
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(cacheNames);
        //set properties
        cacheManager.setCaffeine(cacheVerTokCacheProps());
        return cacheManager;
    }


    //set properties
    public Caffeine<Object, Object> cacheVerTokCacheProps() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(10000)
//                .expireAfterAccess(10, TimeUnit.SECONDS) //lifetime after last access
                .expireAfterWrite(3600, TimeUnit.HOURS) //lifetime after recording
                .weakKeys(); //tells caffeine to create weak references on keys
//                .recordStats();  //record stats about cache
    }

}
