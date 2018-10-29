package com.itty.user.service;

import com.itty.user.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface UserService {
    int insertUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    User findUserByUserName(String username);
}
