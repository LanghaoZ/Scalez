package cc.lzhong.scalez.dao;

import cc.lzhong.scalez.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from User where id=#{id}")
    public User getUserById(@Param("id") Long id);
}
