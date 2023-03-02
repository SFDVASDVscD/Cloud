package com.example.controller;


import com.example.domain.User;
import com.example.service.MessageService;
import com.example.service.UserService;
import com.example.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final RedisTemplate redisTemplate;
    @Autowired
    private final MessageService messageService;

    @Autowired
    public UserController(UserService userService,StringRedisTemplate redisTemplate,MessageService messageService){
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.messageService = messageService;
    }

    /**
     *
     * @param user
     * @param session
     * @return
     */
    @CrossOrigin
    @PostMapping("/login")
     public R<User> login(@RequestBody User user, HttpSession session){
         return userService.login(user,session);
     }

    @CrossOrigin
    @PostMapping("/logout")
    public R<String> logout(){
        redisTemplate.delete("user");
        return R.success("注销成功");
    }

    /**
     * 验证码获取
     * @param user
     * @param session
     * @return
     */
    @CrossOrigin
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session)throws MessagingException {

        return messageService.sendMsg(user,session);
    }

    /**
     * 找回密码时发送的验证码
     * @param user
     * @return
     */
    @CrossOrigin
    @PostMapping("/sendMsg1")
    public R<String> sendMsg1(@RequestBody User user){
        return messageService.sendMsg1(user);
    }
    /**
     * 用户注册
     * @param map
     * @param session
     * @return
     */
    @CrossOrigin
    @PostMapping("/register")
    public R<User> register(@RequestBody Map map, HttpSession session){
        return userService.register(map,session);
    }

    /**
     * 进行邮箱验证
     * @param user
     * @return
     */
    @CrossOrigin
    @PostMapping("/isEmail")
    public R<String> checkEmail(@RequestBody User user){
        return userService.checkEmail(user);
    }

    @CrossOrigin
    @PostMapping("/findPwd")
    public R<String> findPwd(@RequestBody Map map){
        return userService.findPwd(map);
    }

    @CrossOrigin
    @PostMapping("/updateUser")
    public R<User> updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @CrossOrigin
    @PostMapping("/resetPwd")
    public R<String> resetPwd(@RequestBody Map map){
        return userService.resetPwd(map);
    }

    @CrossOrigin
    @PostMapping("/verifyEmail")
    public R<String> verifyEmail(@RequestBody Map map){
       return userService.verifyEmail(map);
    }

    @CrossOrigin
    @PostMapping("/unbind")
    public R<User> unbind(@RequestBody Map map){
        return userService.unbind(map);
    }


    @CrossOrigin
    @GetMapping("/space")
    public R<User> space(){
        return userService.space();
    }

}
