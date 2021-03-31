package cc.lzhong.scalez.util.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

    private static Logger logger = LoggerFactory.getLogger(Receiver.class);

    @RabbitListener(queues = MessageQueueConfig.MESSAGE_QUEUE_NAME)
    public void receiveMessage(String message) {
        logger.info("Message received: " + message);
    }
}
