package com.itty.user.service;

import com.itty.common.utils.MD5Utils;
import com.itty.user.dao.UserMapper;

import com.itty.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    public UserMapper userMapper;

    @Override
    @Transactional
    public int insertUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        user.setPassword(MD5Utils.encryptPassword(user.getPassword()));

        return userMapper.insertSelective(user);
    }

    /**
     * @return com.itty.user.entity.User
     * @Author hezefei
     * @Description 根据用户名查询用户
     * @Date 23:28 2018/8/29
     * @Param [username]
     **/
    @Override
    public User findUserByUserName(String username) {
        User user = null;
        try {
            user = userMapper.selectByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return user;
    }
}
