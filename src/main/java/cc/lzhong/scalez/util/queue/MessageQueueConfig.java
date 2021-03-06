package cc.lzhong.scalez.util.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MessageQueueConfig {

    public static final String MESSAGE_QUEUE_NAME = "queue";
    public static final String SALE_MESSAGE_QUEUE = "sale.queue";

    @Bean
    public Queue queue() {
        return new Queue(MESSAGE_QUEUE_NAME, true);
    }

    @Bean
    public Queue saleQueue() {
        return new Queue(SALE_MESSAGE_QUEUE, true);
    }

}
