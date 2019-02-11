package com.myblog.version3.mapper;

import com.myblog.version3.entity.Permissions;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsMapper {

    @Select("SELECT * FROM myblog.permissions where Uid = #{Uid}")
    Permissions getByUid(String Uid);

    @Insert("INSERT INTO myblog.permissions(ID, Uid, Role, Permissions) VALUES (#{ID} ,#{Uid} ,#{Role} ,#{Permissions})")
    boolean Insert(Permissions permissions);
}
