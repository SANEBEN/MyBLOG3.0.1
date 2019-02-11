package com.myblog.version3.mapper;

import com.myblog.version3.entity.Message;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface messageMapper {

    @Select("SELECT * FROM myblog.message where Uid = #{Uid}")
    Message getByUid(String Uid);
}
