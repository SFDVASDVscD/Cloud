package com.example.controller;


import com.example.domain.File;
import com.example.domain.Manager;
import com.example.domain.User;

import com.example.service.FileService;
import com.example.service.ManagerService;
import com.example.service.UserService;
import com.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private final ManagerService managerService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final FileService fileService;
    @Autowired
    private final RedisTemplate stringRedisTemplate;

    @Autowired
    public ManagerController(ManagerService managerService,UserService userService,FileService fileService,StringRedisTemplate stringRedisTemplate){
        this.fileService = fileService;
        this.userService = userService;
        this.managerService = managerService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     *
     * @param manager
     * @param session
     * @return
     */
    @CrossOrigin
    @PostMapping("/login")
    public R<Manager> login(@RequestBody Manager manager, HttpSession session){
        return managerService.login(manager);
    }

    /**
     * 返回用户集合，展示在manager页面
     * @return
     */
    @CrossOrigin
    @GetMapping
    public R<List<User>> list(){
        return userService.userList();
    }

    /**
     * 接收携带的参数manager
     * 对manager进行更新
     * @param manager
     * @return
     */
    @CrossOrigin
    @PostMapping("/updatemanager")
    public R<Manager> updateManager(@RequestBody Manager manager) {
        return managerService.updateManager(manager);
    }

    /**
     * 接收请求携带的user对象
     * 添加新的用户
     * @param user
     * @return
     */
    @CrossOrigin
    @PostMapping("/adduser")
    public R<String> add(User user){
        return userService.add(user);
    }

    /**
     * 接收请求携带的用户id
     * 根据id删除用户
     * @param user
     * @return
     */
    @CrossOrigin
    @PostMapping("/deleteuser")
    public R<String> delete(@RequestBody User user){
        return userService.delete(user);
    }

    /**
     * 接收请求携带的user对象
     * 更新user对象信息
     * @param user
     * @return
     */
    @CrossOrigin
    @PostMapping("/user")
    public R<String> update(@RequestBody User user){
        return userService.update(user);
    }


    @CrossOrigin
    @GetMapping("/getuser")
    public R<User> query(Long id){
        return userService.query(id);
    }

    @CrossOrigin
    @GetMapping("/getuserfile")
    public R<List<File>> fileList(Long id){
       return fileService.fileList(id);
    }

    @CrossOrigin
    @PutMapping("/logout")
    public R<String> logout() {
        stringRedisTemplate.delete("manager");
        return R.success("登出成功");
    }


}
