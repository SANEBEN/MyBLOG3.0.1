package com.myblog.version3.mapper;

import com.myblog.version3.entity.Category;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public interface categoryMapper {

    @Select("SELECT * FROM myblog.category where ID = #{ID}")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Uid", property = "Uid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR)
    })
    Category getByID(String ID);

    @Select("SELECT * FROM myblog.category where Uid = #{Uid}")
    @Results({
            @Result(column = "ID", property = "ID", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "Uid", property = "Uid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ID", property = "articles", javaType = List.class,
                    many = @Many(select = "com.myblog.version3.mapper.articleMapper.getByCid"))
    })
    List<Category> getByUid(String Uid);

    @Insert("INSERT INTO myblog.category(ID, Uid, name) VALUES (#{ID} ,#{Uid} ,#{name})")
    boolean Insert(Category category) throws SQLIntegrityConstraintViolationException;

    @Delete("DELETE FROM myblog.category where ID = #{Cid}")
    boolean delete(String Cid);
}
