package cc.lzhong.scalez.util.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MessageQueueConfig {

    public static final String MESSAGE_QUEUE_NAME = "queue";

    @Bean
    public Queue queue() {
        return new Queue(MESSAGE_QUEUE_NAME, true);
    }

}
