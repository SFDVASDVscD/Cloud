package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.Jpa.UserJpa;
import com.example.dao.UserDao;
import com.example.domain.User;
import com.example.service.MessageService;
import com.example.service.UserService;
import com.example.utils.R;
import com.example.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    private final UserDao dao;
    private final UserJpa jpa;
    @Autowired
    private final RedisTemplate redisTemplate;

    @Autowired
    public UserServiceImpl(UserDao dao, UserJpa jpa,RedisTemplate redisTemplate){
        this.dao = dao;
        this.jpa = jpa;
        this.redisTemplate = redisTemplate;
    }

    public R<List<User>> userList(){
        return R.success(jpa.findAll());
    }

    public R<String> add(User user){
        int i = dao.insert(user);
        if (i >= 1)
            return R.success("添加成功！");
        else
            return R.error("添加失败！");
    }

    public R<String> delete(User user){
        if (dao.deleteById(user) >= 1)
            return R.success("删除成功！");
        else
            return R.error("删除失败！");
    }

    public R<String> update(User user){
        if (dao.updateById(user) >= 1)
            return R.success("更新成功！");
        else
            return R.error("更新失败！");
    }

    public R<User> query(Long id){
        return R.success(dao.selectById(id));
    }

    public R<User> login(User user, HttpSession session){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName()).eq(User::getPassword,user.getPassword());
        User user1=dao.selectOne(queryWrapper);
        if(user1!=null){
            if(user1.getStatus()==0){
                return R.error("该账号已被禁用");
            }
            redisTemplate.opsForValue().set("user",user1.getId(),24, TimeUnit.HOURS);
            return R.success(user1);
        }
        return R.error("登录失败");
    }

    public R<User> register(Map map, HttpSession session){
        log.info(map.toString());
        String userName=map.get("userName").toString();
        String password=map.get("password").toString();
        //获取邮箱
        String email = map.get("email").toString();
        //获取验证码
        String code = map.get("code").toString();

        //redis中得到缓存的验证码
        Object codeInSession=redisTemplate.opsForValue().get(email);

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            //如果能够比对成功，说明验证码获取成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUserName,userName);

            User user = dao.selectOne(queryWrapper);
            if(user==null){
                user=new User();
                user.setUserName(userName);
                user.setPassword(password);
                user.setEmail(email);
                user.setStatus(1);
                this.save(user);
            }
            else{
                return R.error("用户名已存在");
            }
            session.setAttribute("user",user.getId());

            //用户登录成功，删除缓存的验证码
            redisTemplate.delete(email);
            return R.success(user);
        }
        return R.error("验证码错误");
    }

    public R<String> checkEmail(User user){
        //获取邮箱
        String email = user.getEmail();

        if(StringUtils.isNotEmpty(email)){
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail,user.getEmail());
            if(this.getOne(queryWrapper)!=null){
                return R.success("邮箱存在");
            }
            return R.error("邮箱不存在");
        }
        return R.error("邮箱不能为空");
    }

    public R<String> findPwd(Map map){
        log.info(map.toString());
        String password=map.get("NewPwd").toString();
        //获取邮箱
        String email = map.get("Email").toString();
        //获取验证码
        String code = map.get("Code").toString();

        //redis中得到缓存的验证码
        Object codeInSession=redisTemplate.opsForValue().get(email);
        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            //如果能够比对成功，说明验证码获取成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail,email);

            User user = this.getOne(queryWrapper);
            user.setPassword(password);
            this.updateById(user);
            //用户登录成功，删除缓存的验证码
            redisTemplate.delete(email);
            return R.success("修改密码成功");
        }
        return R.error("验证码错误");
    }

    public R<User> updateUser(User user){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        User user1=this.getOne(queryWrapper);
        user1.setPhone(user.getPhone());
        user1.setRealName(user.getRealName());
        this.updateById(user1);
        user1=this.getOne(queryWrapper);
        return R.success(user1);
    }

    public R<String> resetPwd(Map map){
        String oldPwd=map.get("oldPwd").toString();
        String newPwd=map.get("newPwd").toString();
        String id=map.get("id").toString();
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,id);
        User user=this.getOne(queryWrapper);
        if(oldPwd.equals(user.getPassword())){
            if(oldPwd.equals(newPwd)){
                return R.error("请输入与旧密码不相同的新密码");
            }
            user.setPassword(newPwd);
            this.updateById(user);
            return R.success("修改密码成功");
        }
        return R.error("请输入正确的旧密码");
    }

    public R<String> verifyEmail(Map map){
        //获取邮箱
        String email = map.get("email").toString();
        //获取验证码
        String code = map.get("code").toString();
        //redis中得到缓存的验证码
        Object codeInSession=redisTemplate.opsForValue().get(email);

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){

            return R.success("下一步");
        }
        return R.error("验证码错误");
    }

    public R<User> unbind(Map map){
        //获取邮箱
        String email = map.get("Email").toString();
        String oldEmail = map.get("OldEmail").toString();
        //获取验证码
        String code = map.get("Code").toString();
        //redis中得到缓存的验证码
        Object codeInSession=redisTemplate.opsForValue().get(email);

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail,oldEmail);
            User user=this.getOne(queryWrapper);
            user.setEmail(email);
            this.updateById(user);
            return R.success(user);
        }
        return R.error("验证码错误");
    }

    public R<User> space(){
        Object userid =redisTemplate.opsForValue().get("user");
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,userid);
        User user=this.getOne(queryWrapper);
        return R.success(user);
    }

}
