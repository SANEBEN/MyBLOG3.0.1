package com.myblog.version3.mapper;

import com.myblog.version3.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userMapper {

    @Select("SELECT * FROM myblog.user WHERE ID = #{ID}")
    User getByID(@Param("ID") String ID);

    @Select("Select * FROM myblog.user WHERE phone = #{phone}")
    User getByPhone(@Param("phone") String phone);

    @Select("Select * FROM myblog.user")
    List<User> getAll();

    @Insert("insert into myblog.user(ID, password, phone) values (#{ID} ,#{password} ,#{phone})")
    boolean insert(User user);

    @Select("select count(*) from  myblog.user where phone = #{phone}")
    int DuplicateChecking(String phone);

    @Update("update myblog.user set password = #{password} where ID = #{Uid}")
    boolean updatePassword(String password ,String Uid);

    @Delete("DELETE FROM myblog.user where ID = #{ID}")
    boolean delete(String ID);
}
