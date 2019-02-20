package com.myblog.version3.mapper;

import com.myblog.version3.entity.Article;
import com.myblog.version3.entity.Comment;
import com.myblog.version3.entity.Message;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface commentMapper {

    @Select("SELECT * FROM myblog.comment where Aid = #{Aid} ORDER BY time")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Aid", property = "Aid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Aid", property = "article", javaType = Article.class,
                    one = @One(select = "com.myblog.version3.mapper.articleMapper.getByID")),
            @Result(column = "Uid", property = "Uid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Uid", property = "user", javaType = Message.class,
                    one = @One(select = "com.myblog.version3.mapper.messageMapper.getByUid")),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "time", property = "time", jdbcType = JdbcType.DATETIMEOFFSET),
            @Result(column = "ID", property = "replies", javaType = List.class,
                    many = @Many(select = "com.myblog.version3.mapper.replyMapper.getByCid"))
    })
    List<Comment> getByAid(String Aid);

    @Select("SELECT * FROM myblog.comment where ID =#{ID}")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Aid", property = "Aid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Aid", property = "article", javaType = Article.class,
                    one = @One(select = "com.myblog.version3.mapper.articleMapper.getByID")),
            @Result(column = "Uid", property = "Uid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Uid", property = "user", javaType = Message.class,
                    one = @One(select = "com.myblog.version3.mapper.messageMapper.getByUid")),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "time", property = "time", jdbcType = JdbcType.DATETIMEOFFSET),
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
