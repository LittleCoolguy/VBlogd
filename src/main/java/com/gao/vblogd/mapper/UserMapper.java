package com.gao.vblogd.mapper;

import com.gao.vblogd.Entity.Role;
import com.gao.vblogd.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectByUsername(@Param("username") String username);
    List<User> GetUserByNickName(@Param("nickname") String s);
    Integer updateEnabledByUid(@Param("enabled") boolean enabled,@Param("uid") Long uid);

    List<Role> getAllRoles();

    int deleteUserRolesByUid(Long id);

    int setUserRoles(Long[] rids, Long id);

    User getById(@Param("id") Long id);

    int DeleteUserByUid(@Param("uid") Long uid);
}
