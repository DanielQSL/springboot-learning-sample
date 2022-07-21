package com.qsl.springboot.security.service;

import com.qsl.springboot.security.model.User;
import com.qsl.springboot.security.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author DanielQSL
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String login(User user) {
        // AuthenticationManager.authenticate()进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 如果认证没通过，给出相应提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        // 如果认证通过，使用userId生成一个jwt，将jwt返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        final String userId = loginUser.getUser().getId().toString();

        // 将完整的用户信息存入redis userId作为key
        redisTemplate.opsForValue().set("login:" + userId, loginUser);

        return jwtUtils.generateToken(userId);
    }

    @Override
    public void logout() {
        // 1.获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        final Long userId = loginUser.getUser().getId();
        // 2.删除redis中登录用户的信息，即退出
        redisTemplate.delete("login:" + userId);
    }

}
