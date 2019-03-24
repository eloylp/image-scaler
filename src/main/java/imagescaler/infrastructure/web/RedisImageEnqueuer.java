package imagescaler.infrastructure.web;

import imagescaler.domain.Image;
import imagescaler.domain.ImageEnqueuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisImageEnqueuer implements ImageEnqueuer {

    @Value("${scaler.topic}")
    private String topic;

    private StringRedisTemplate redisTemplate;

    @Autowired
    RedisImageEnqueuer(@Qualifier("redisWeb") StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void perform(Image image) {
        this.redisTemplate.convertAndSend(this.topic, image.getUuid());
    }
}
