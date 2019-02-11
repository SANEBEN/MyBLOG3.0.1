package com.myblog.version3.mapper;

import com.myblog.version3.entity.UserActivity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userActivityMapper {

    @Select("SELECT * FROM myblog.useractivity where Uid = #{Uid}")
    List<UserActivity> getByUid(String Uid);

}
