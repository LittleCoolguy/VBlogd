package com.gao.vblogd.mapper;

import com.gao.vblogd.Entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RoleMapper {
    List<Role> GetRolesByUid(Long uid);

}
