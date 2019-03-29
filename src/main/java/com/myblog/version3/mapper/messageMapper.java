package com.myblog.version3.mapper;

import com.myblog.version3.entity.Message;
import org.apache.ibatis.annotations.Delete;
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

    @Select("SELECT count(*) FROM myblog.message")
    int statistical();

    @Insert("insert into myblog.message(ID, Uid, phone, userName, createdTime)" +
            " values(#{ID} ,#{Uid} ,#{phone} ,#{userName} ,#{createdTime})")
    boolean insert(Message message);

    @Update("update myblog.message set Role = 'admin' where Uid = #{Uid}")
    boolean setAdmin(String Uid);

    @Update("update myblog.message set Role = 'user' where Uid = #{Uid}")
    boolean setUser(String Uid);

    @Update("update myblog.message set userName = #{userName} ,email = #{email},introduce = #{introduce} where Uid = #{Uid}")
    boolean update(Message message);

    @Update("update myblog.message set img = #{img} where Uid = #{Uid}")
    boolean updateHeadPortrait(String img ,String Uid);

    @Delete("DELETE from myblog.message where Uid = #{Uid}")
    boolean delete(String Uid);

    @Update("update myblog.message set status = #{status} where Uid = #{Uid}")
    boolean userStatus(String Uid ,int status);
}
