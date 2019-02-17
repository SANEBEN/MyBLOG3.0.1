package com.myblog.version3.mapper;

import com.myblog.version3.entity.Reply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface replyMapper {

    @Select("SELECT * FROM myblog.reply where Cid = #{Cid}")
    List<Reply> getByCid(String Cid);

    @Select("SELECT * FROM myblog.reply where ID = #{ID}")
    Reply getByID(String ID);

    @Insert("insert into myblog.reply(ID, Cid, parent_reply_id, reply_id, content, time)" +
            " values (#{ID} ,#{Cid} ,#{parent_reply_id} ,#{reply_id} ,#{content} ,#{time})")
    Boolean insert(Reply reply);

    @Delete("DELETE from myblog.reply where ID=#{ID}")
    Boolean delete(String ID);
}
