package com.myblog.version3.mapper;

import com.myblog.version3.entity.UserActivity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userActivityMapper {

    @Select("SELECT * FROM myblog.useractivity where Uid = #{Uid}")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Uid", property = "Uid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "action", property = "action", jdbcType = JdbcType.VARCHAR),
            @Result(column = "object_id", property = "object_id", jdbcType = JdbcType.VARCHAR),
            @Result(column = "operation_object_id", property = "operation_object_id", jdbcType = JdbcType.VARCHAR),
            @Result(column = "created_time", property = "created_time", jdbcType = JdbcType.DATETIMEOFFSET),
    })
    List<UserActivity> getByUid(String Uid);

    @Insert("insert into myblog.useractivity(ID, Uid ,action, object_id, created_time)" +
            "VALUES(#{ID} ,#{Uid} ,#{action} ,#{object_id} ,#{created_time})")
    void Article(UserActivity activity);

    @Insert("insert into myblog.useractivity(ID, Uid ,action, object_id ,operation_object_id ,created_time)" +
            "VALUES(#{ID} ,#{Uid} ,#{action} ,#{object_id} ,#{operation_object_id} ,#{created_time})")
    void Other(UserActivity activity);
}
