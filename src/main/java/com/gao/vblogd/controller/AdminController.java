package com.gao.vblogd.controller;

import com.gao.vblogd.Entity.Article;
import com.gao.vblogd.Entity.RespBean;
import com.gao.vblogd.Entity.Role;
import com.gao.vblogd.Entity.User;
import com.gao.vblogd.service.ArticleService;
import com.gao.vblogd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public List<User> GetUserByNickName(String nickname){
        System.out.println("执行到这");
        List<User> users = userService.GetUserByNickName(nickname);
        System.out.println(nickname);
        return users;
    }
    @RequestMapping(value = "/user/enabled",method = RequestMethod.PUT)
    public RespBean updateUserEnabled(boolean enabled,Long uid){
       if (userService.updateUserEnabled(enabled,uid)==1)
           return new RespBean("success","更新成功");
       else return new RespBean("fail","更新失败");
    }
    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    public List<Role> getAllRoles(){
        return userService.getAllRoles();
    }
    @RequestMapping(value = "/user/role",method = RequestMethod.PUT)
    public RespBean updateUserRole(Long[] rids,Long id){
            if (userService.updateUserRole(rids,id)==rids.length)
                return new RespBean("success","更新成功");
            else return new RespBean("fail","更新失败");
    }
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public User getById(@PathVariable Long id){
        return userService.getById(id);
    }
    @RequestMapping(value = "/user/{uid}", method = RequestMethod.DELETE)
    public RespBean DeleteUserByUid(@PathVariable Long uid){
        if (userService.DeleteUserByUid(uid)==1)
            return new RespBean("success","更新成功");
        else return new RespBean("fail","更新失败");
    }
}
