package com.myblog.version3.mapper;

import com.myblog.version3.entity.Article;
import com.myblog.version3.entity.Category;
import com.myblog.version3.entity.Message;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface articleMapper {

    @Select("SELECT * FROM myblog.article where ID = #{ID}")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Cid", property = "Cid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Cid", property = "category", javaType = Category.class,
                    one = @One(select = "com.myblog.version3.mapper.categoryMapper.getByID")),
            @Result(column = "Uid", property = "Uid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Uid", property = "user", javaType = Message.class,
                    one = @One(select = "com.myblog.version3.mapper.messageMapper.getByUid")),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "URL", property = "URL", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createdTime", property = "createdTime", jdbcType = JdbcType.DATETIMEOFFSET),
            @Result(column = "changeTime", property = "changeTime", jdbcType = JdbcType.DATETIMEOFFSET),
            @Result(column = "hits", property = "hits", jdbcType = JdbcType.INTEGER),
            @Result(column = "isPrivate", property = "isPrivate", jdbcType = JdbcType.TINYINT),
            @Result(column = "allComment", property = "allComment", jdbcType = JdbcType.TINYINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    Article getByID(String ID);

    @Select("SELECT * FROM myblog.article where Uid = #{Uid}")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Cid", property = "Cid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Cid", property = "category", javaType = Category.class,
                    one = @One(select = "com.myblog.version3.mapper.categoryMapper.getByID")),
            @Result(column = "Uid", property = "Uid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Uid", property = "user", javaType = Message.class,
                    one = @One(select = "com.myblog.version3.mapper.messageMapper.getByUid")),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "URL", property = "URL", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createdTime", property = "createdTime", jdbcType = JdbcType.DATETIMEOFFSET),
            @Result(column = "changeTime", property = "changeTime", jdbcType = JdbcType.DATETIMEOFFSET),
            @Result(column = "hits", property = "hits", jdbcType = JdbcType.INTEGER),
            @Result(column = "isPrivate", property = "isPrivate", jdbcType = JdbcType.TINYINT),
            @Result(column = "allComment", property = "allComment", jdbcType = JdbcType.TINYINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<Article> getByUid(String Uid);

    @Select("SELECT * FROM myblog.article where Cid = #{Cid}")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Cid", property = "Cid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Uid", property = "Uid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Uid", property = "user", javaType = Message.class,
                    one = @One(select = "com.myblog.version3.mapper.messageMapper.getByUid")),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "URL", property = "URL", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createdTime", property = "createdTime", jdbcType = JdbcType.DATETIMEOFFSET),
            @Result(column = "changeTime", property = "changeTime", jdbcType = JdbcType.DATETIMEOFFSET),
            @Result(column = "hits", property = "hits", jdbcType = JdbcType.INTEGER),
            @Result(column = "isPrivate", property = "isPrivate", jdbcType = JdbcType.TINYINT),
            @Result(column = "allComment", property = "allComment", jdbcType = JdbcType.TINYINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<Article> getByCid(String Cid);

    @Select("SELECT * FROM myblog.article")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Cid", property = "Cid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Cid", property = "category", javaType = Category.class,
                    one = @One(select = "com.myblog.version3.mapper.categoryMapper.getByID")),
            @Result(column = "Uid", property = "Uid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Uid", property = "user", javaType = Message.class,
                    one = @One(select = "com.myblog.version3.mapper.messageMapper.getByUid")),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "URL", property = "URL", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createdTime", property = "createdTime", jdbcType = JdbcType.DATETIMEOFFSET),
            @Result(column = "changeTime", property = "changeTime", jdbcType = JdbcType.DATETIMEOFFSET),
            @Result(column = "hits", property = "hits", jdbcType = JdbcType.INTEGER),
            @Result(column = "isPrivate", property = "isPrivate", jdbcType = JdbcType.TINYINT),
            @Result(column = "allComment", property = "allComment", jdbcType = JdbcType.TINYINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER)
    })
    List<Article> getAll();

    @Insert("insert into myblog.article(ID, Cid, Uid, title, URL, createdTime, changeTime ,isPrivate ,allowComment) VALUES (#{ID} ,#{Cid} ,#{Uid} ,#{title} ,#{URL} ,#{createdTime} ,#{changeTime} ,#{isPrivate} ,#{allowComment})")
    Boolean insert(Article article);

    @Update("update myblog.article set URL=#{URL},title=#{title},changeTime=#{changeTime},Cid=#{Cid},isPrivate=#{isPrivate},allowComment=#{allowComment} where ID=#{ID}")
    Boolean update(Article article);

    @Delete("DELETE FROM myblog.article where ID = #{Aid}")
    Boolean deleteByAid(String Aid);

    @Delete("DELETE FROM myblog.article where Cid = #{Cid}")
    Boolean deleteByCid(String Cid);

    @Update("update myblog.article set Status = #{status} where ID = #{ID}")
    Boolean status(Integer status, String ID);

    @Update("update myblog.article set allowComment = #{comment} where ID = #{ID}")
    Boolean commentStatus(Boolean comment, String ID);

    @Update("update myblog.article set isPrivate = #{isPrivate} where ID = #{ID}")
    Boolean setPrivate(Boolean isPrivate, String ID);

    @Update("update myblog.article set hits = hits + 1 where ID = #{ID}")
    Boolean hit(String Id);
}
