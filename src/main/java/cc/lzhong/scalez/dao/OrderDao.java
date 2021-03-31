package cc.lzhong.scalez.dao;

import cc.lzhong.scalez.domain.Order;
import cc.lzhong.scalez.domain.OrderDetail;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {

    @Select("select * from `Order` where user_id=#{userId} and product_id=#{productId}")
    public Order getOrderByUserAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);

    @Select("select * from `OrderDetail` where id=#{orderId}")
    public OrderDetail getOrderById(@Param("orderId") Long orderId);

    @Insert("insert into OrderDetail(user_id, product_id, product_name, status, create_time) " +
            "values(#{userId}, #{productId}, #{productName}, #{status}, #{createTime})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = Long.class, before = false, statement = "select last_insert_id()")
    public Long insertOrderDetail(OrderDetail orderDetail);

    @Insert("insert into `Order`(user_id, product_id, order_id) " +
            "values(#{userId}, #{productId}, #{orderId})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = Long.class, before = false, statement = "select last_insert_id()")
    public int insertOrder(Order order);

}
