package com.gao.vblogd.service;

import com.gao.vblogd.Entity.Role;
import com.gao.vblogd.Entity.User;
import com.gao.vblogd.mapper.RoleMapper;
import com.gao.vblogd.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    /**
     * 这部分应该是由两个查询语句组成(用户+用户权限)
     * 封装成UserDetails对象返回
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userMapper.selectByUsername(s);
        if (user==null)
            return new User();
        System.out.println(user.getId());
        List<Role> roles= roleMapper.GetRolesByUid(user.getId());
        user.setRoles(roles);
        return user;
    }
    public List<User> GetUserByNickName(String s){
        List<User> users = userMapper.GetUserByNickName(s);
        return users;
    }
    public int updateUserEmail(String email) {//尚未实现
        return 1;
    }

    public Integer updateUserEnabled(boolean enabled, Long uid) {
        Integer integer = userMapper.updateEnabledByUid(enabled, uid);
        return integer;
    }

    public List<Role> getAllRoles() {
        return userMapper.getAllRoles();
    }

    public int updateUserRole(Long[] rids, Long id) {
        int i=userMapper.deleteUserRolesByUid(id);
        return userMapper.setUserRoles(rids,id);
    }

    public User getById(Long id) {
        return userMapper.getById(id);
    }

    public int DeleteUserByUid(Long uid) {
        return userMapper.DeleteUserByUid(uid);
    }
}
