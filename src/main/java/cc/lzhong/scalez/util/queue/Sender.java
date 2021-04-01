package cc.lzhong.scalez.util.queue;

import cc.lzhong.scalez.util.common.StringValueConverter;
import cc.lzhong.scalez.util.queue.message.SaleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    private final AmqpTemplate amqpTemplate;

    private static Logger logger = LoggerFactory.getLogger(Receiver.class);

    public Sender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMessage(Object message) {
        String convertedMessage = StringValueConverter.convertValueToString(message);
        amqpTemplate.convertAndSend(MessageQueueConfig.MESSAGE_QUEUE_NAME, convertedMessage);
        logger.info("Message sent: " + convertedMessage);
    }

    public void sendSaleMessage(SaleMessage message) {
        String convertedMessage = StringValueConverter.convertValueToString(message);
        amqpTemplate.convertAndSend(MessageQueueConfig.SALE_MESSAGE_QUEUE, convertedMessage);
        logger.info("Message sent: " + convertedMessage);
    }

}
