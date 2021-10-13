package com.gao.vblogd.controller;

import com.gao.vblogd.Entity.RespBean;
import com.gao.vblogd.Utils.Util;
import com.gao.vblogd.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/currentUserName")
    public String currentUserName() {
        return Util.getCurrentUser().getNickname();
    }

    @RequestMapping("/currentUserId")
    public Long currentUserId() {
        return Util.getCurrentUser().getId();
    }

    @RequestMapping("/currentUserEmail")
    public String currentUserEmail() {
        return Util.getCurrentUser().getEmail();
    }

    @RequestMapping("/isAdmin")
    public Boolean isAdmin() {
        List<GrantedAuthority> authorities = Util.getCurrentUser().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().contains("超级管理员")) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/updateUserEmail",method = RequestMethod.PUT)
    public RespBean updateUserEmail(String email) {
        if (userService.updateUserEmail(email) == 1) {
            return new RespBean("success", "开启成功!");
        }
        return new RespBean("error", "开启失败!");
    }
}
