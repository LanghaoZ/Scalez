package cc.lzhong.scalez.dao;

import cc.lzhong.scalez.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductDao {

    @Select("select * from Product where id=#{id}")
    public Product getProductById(@Param("id") Long id);

    @Select("select * from Product")
    public List<Product> getAllProduct();

    @Update("update Product set count = count - 1 where id=#{id}")
    public int decrementCount(Product product);

    @Update("update Product set count = count - 1 where id=#{id} and count > 0")
    public int decrementCountSafe(Product product);
}
