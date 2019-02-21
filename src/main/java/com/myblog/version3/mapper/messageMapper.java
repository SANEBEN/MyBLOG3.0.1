package com.myblog.version3.mapper;

import com.myblog.version3.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface messageMapper {

    @Select("SELECT * FROM myblog.message where Role = 'user' ORDER BY createdTime")
    List<Message> getAllUser();

    @Select("SELECT * FROM myblog.message where Role = 'admin' ORDER BY createdTime")
    List<Message> getAllAdmin();

    @Select("SELECT * FROM myblog.message where Uid = #{Uid}")
    Message getByUid(String Uid);

    @Insert("insert into myblog.message(ID, Uid, phone, userName, createdTime)" +
            " values(#{ID} ,#{Uid} ,#{phone} ,#{userName} ,#{createdTime})")
    boolean insert(Message message);

    @Update("update myblog.message set userName = #{userName} ,email = #{email} ,img = #{img} ,introduce = #{introduce} where Uid = #{Uid}")
    boolean update(Message message);
}
