package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.MessageDao;
import com.example.dao.UserDao;
import com.example.domain.Message;
import com.example.domain.User;
import com.example.service.MessageService;
import com.example.utils.R;
import com.example.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements MessageService {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;
    @Autowired
    private final RedisTemplate redisTemplate;
    private final UserDao userDao;

    public MessageServiceImpl(JavaMailSender javaMailSender,StringRedisTemplate redisTemplate,UserDao userDao){
        this.javaMailSender = javaMailSender;
        this.redisTemplate = redisTemplate;
        this.userDao = userDao;
    }

    public R<String> sendMsg(User user, HttpSession session) {
        SimpleMailMessage message=new SimpleMailMessage();
        String email = user.getEmail();
        if(StringUtils.isNotEmpty(email)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail, user.getEmail());
            if (userDao.selectOne(queryWrapper) == null) {
                String code = ValidateCodeUtils.generateValidateCode(4).toString();
                log.info("code={}", code);
                message.setFrom(from);
                message.setTo(user.getEmail());
                message.setSubject("验证码");
                message.setText(code);
                redisTemplate.opsForValue().set(user.getEmail(), code, 5, TimeUnit.MINUTES);
                javaMailSender.send(message);
                return R.success("邮箱验证码发送成功，请及时查看!");
            } else {
                return R.error("该邮箱已被注册!");
            }
        }
        return R.error("邮箱验证码发送失败!");
    }

    public R<String> sendMsg1(User user){
        //获取邮箱
        String email = user.getEmail();
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        log.info("code={}",code);

        /**
         * 向邮箱发送验证码
         */
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject("验证码");
        message.setText(code);

        //redis缓存验证码和邮箱
        redisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);

        javaMailSender.send(message);
        return R.success(null);
    }

}
