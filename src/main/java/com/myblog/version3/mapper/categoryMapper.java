package com.myblog.version3.mapper;

import com.myblog.version3.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public interface categoryMapper {

    @Select("SELECT * FROM myblog.category where ID = #{ID}")
    Category getByID(String ID);

    @Select("SELECT * FROM myblog.category where Uid = #{Uid}")
    List<Category> getByUid(String Uid);

    @Insert("INSERT INTO myblog.category(ID, Uid, name) VALUES (#{ID} ,#{Uid} ,#{name})")
    boolean Insert(Category category) throws SQLIntegrityConstraintViolationException;
}
