package com.myblog.version3.mapper;

import com.myblog.version3.entity.Category;
import com.myblog.version3.entity.Comment;
import com.myblog.version3.entity.Message;
import com.myblog.version3.entity.Reply;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface replyMapper {

    @Select("SELECT * FROM myblog.reply where Cid = #{Cid} ORDER BY time")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Cid", property = "Cid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_reply_id", property = "parent_reply_id", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_reply_id", property = "parent_reply", javaType = Message.class,
                    one = @One(select = "com.myblog.version3.mapper.messageMapper.getByUid")),
            @Result(column = "reply_id", property = "reply_id", jdbcType = JdbcType.VARCHAR),
            @Result(column = "reply_id", property = "reply", javaType = Message.class,
                    one = @One(select = "com.myblog.version3.mapper.messageMapper.getByUid")),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "time", property = "time", jdbcType = JdbcType.DATETIMEOFFSET),
    })
    List<Reply> getByCid(String Cid);

    @Select("SELECT * FROM myblog.reply where ID = #{ID}")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Cid", property = "Cid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_reply_id", property = "parent_reply_id", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_reply_id", property = "parent_reply", javaType = Message.class,
                    one = @One(select = "com.myblog.version3.mapper.messageMapper.getByUid")),
            @Result(column = "reply_id", property = "reply_id", jdbcType = JdbcType.VARCHAR),
            @Result(column = "reply_id", property = "reply", javaType = Message.class,
                    one = @One(select = "com.myblog.version3.mapper.messageMapper.getByUid")),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "time", property = "time", jdbcType = JdbcType.DATETIMEOFFSET),
    })
    Reply getByID(String ID);

    @Insert("insert into myblog.reply(ID, Cid, parent_reply_id, reply_id, content, time)" +
            " values (#{ID} ,#{Cid} ,#{parent_reply_id} ,#{reply_id} ,#{content} ,#{time})")
    Boolean insert(Reply reply);

    @Delete("DELETE from myblog.reply where ID=#{ID}")
    Boolean delete(String ID);
}
