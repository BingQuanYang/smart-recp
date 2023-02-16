package com.smart.recp.service.user.controller;

import com.smart.recp.service.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/user/user")
public class UserController {
    @Resource
    UserService userService;
}
