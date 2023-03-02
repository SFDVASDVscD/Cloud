package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.User;
import com.example.utils.R;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


public interface UserService extends IService<User> {

    R<List<User>> userList();

    R<String> add(User user);

    R<String> delete(User user);

    R<String> update(User user);

    R<User> query(Long id);

    R<User> login(User user, HttpSession session);


    R<User> register(Map map, HttpSession session);

    R<String> checkEmail(User user);

    R<String> findPwd(Map map);

    R<User> updateUser(User user);

    R<String> resetPwd(Map map);

    R<String> verifyEmail(Map map);

    R<User> unbind(Map map);

    R<User> space();

}
