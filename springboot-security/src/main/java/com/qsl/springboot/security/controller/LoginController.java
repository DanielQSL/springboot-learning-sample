package com.qsl.springboot.security.controller;

import com.qsl.project.base.model.CommonResponse;
import com.qsl.springboot.security.model.User;
import com.qsl.springboot.security.service.LoginService;
import com.qsl.springboot.security.utils.JsonUtil;
import com.qsl.springboot.security.utils.JwtUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录接口
 *
 * @author DanielQSL
 */
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private JwtUtils jwtUtils;

    @PostMapping("/user/login")
    public CommonResponse<String> login(@RequestBody User user) {
        return CommonResponse.success(loginService.login(user));
    }

    @PostMapping("/user/logout")
    public CommonResponse<Void> logout() {
        loginService.logout();
        return CommonResponse.success();
    }

    @PostMapping("/jwt/genToken")
    public CommonResponse<String> genToken(@RequestBody User user) {
        return CommonResponse.success(jwtUtils.generateToken(JsonUtil.toJsonString(user)));
    }

    @PostMapping("/jwt/parseToken")
    public CommonResponse<String> parseToken(@RequestParam("token") String token) {
        return CommonResponse.success(jwtUtils.getUserInfoFromToken(token, String.class));
    }

}
