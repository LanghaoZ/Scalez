package cc.lzhong.scalez.dao;

import cc.lzhong.scalez.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductDao {

    @Select("select * from Product")
    public List<Product> getAllProduct();
}
