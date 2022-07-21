package com.qsl.springboot.security.service;

import com.qsl.springboot.security.model.User;

/**
 * @author DanielQSL
 */
public interface LoginService {

    String login(User user);

    void logout();

}
