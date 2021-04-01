package cc.lzhong.scalez.util.queue;

import cc.lzhong.scalez.domain.OrderDetail;
import cc.lzhong.scalez.domain.Product;
import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.service.ProductService;
import cc.lzhong.scalez.service.RedisService;
import cc.lzhong.scalez.service.SaleService;
import cc.lzhong.scalez.util.common.StringValueConverter;
import cc.lzhong.scalez.util.queue.message.SaleMessage;
import cc.lzhong.scalez.util.redis.OrderKeyPrefix;
import cc.lzhong.scalez.util.response.AppMessage;
import cc.lzhong.scalez.util.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

    private static Logger logger = LoggerFactory.getLogger(Receiver.class);
    private final ProductService productService;
    private final SaleService saleService;
    private final RedisService redisService;

    public Receiver(ProductService productService, SaleService saleService,
                    RedisService redisService) {
        this.productService = productService;
        this.saleService = saleService;
        this.redisService = redisService;
    }

    @RabbitListener(queues = MessageQueueConfig.MESSAGE_QUEUE_NAME)
    public void receiveMessage(String message) {
        logger.info("Message received: " + message);
    }

    @RabbitListener(queues = MessageQueueConfig.SALE_MESSAGE_QUEUE)
    public void receiveSaleMessage(String message) {
        logger.info("Message received: " + message);
        SaleMessage saleMessage = StringValueConverter.convertStringToValue(message, SaleMessage.class);
        User user = saleMessage.getUser();
        Long productId = saleMessage.getProductId();
        String uuid = saleMessage.getUuid();

        Product product = productService.getProductById(productId);
        int count = product.getCount();
        if (count <= 0) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(0L);
            orderDetail.setProductName("");
            redisService.set(OrderKeyPrefix.uuid, uuid, orderDetail);
            return ;
        }

        saleService.sellV3Safe(user, product, uuid);
    }
}
