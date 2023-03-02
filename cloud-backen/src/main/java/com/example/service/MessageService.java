package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Message;
import com.example.domain.User;
import com.example.utils.R;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

public interface MessageService extends IService<Message> {

    R<String> sendMsg(User user, HttpSession session)throws MessagingException;

    R<String> sendMsg1(User user);

}
