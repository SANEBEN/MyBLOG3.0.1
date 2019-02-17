package com.myblog.version3.mapper;

import com.myblog.version3.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface commentMapper {

    @Select("SELECT * FROM myblog.comment where Aid = #{Aid}")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Aid", property = "Aid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Uid", property = "Uid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "time", property = "time", jdbcType = JdbcType.DATE),
            @Result(column = "ID", property = "replies", javaType = List.class,
                    many = @Many(select = "com.myblog.version3.mapper.replyMapper.getByCid"))
    })
    List<Comment> getByAid(String Aid);

    @Select("SELECT * FROM myblog.comment where ID =#{ID}")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Aid", property = "Aid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Uid", property = "Uid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "time", property = "time", jdbcType = JdbcType.DATE),
            @Result(column = "ID", property = "replies", javaType = List.class,
                    many = @Many(select = "com.myblog.version3.mapper.replyMapper.getByCid"))

    })
    Comment getByID(String ID);

    @Insert("INSERT INTO myblog.comment(ID, Aid, Uid, content, time)" +
            " VALUES (#{ID} ,#{Aid} ,#{Uid} ,#{content} ,#{time})")
    Boolean insert(Comment comment);

    @Delete("DELETE FROM myblog.comment where ID = #{ID}")
    Boolean delete(String ID);
}
