package com.example.service.impl;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.dao.Jpa.ManagerJpa;
import com.example.dao.ManagerDao;
import com.example.domain.Manager;

import com.example.service.ManagerService;
import com.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerDao, Manager> implements ManagerService {

    private final ManagerJpa jpa;
    private final ManagerDao dao;
    @Autowired
    private final RedisTemplate redisTemplate;

    @Autowired
    public ManagerServiceImpl(ManagerJpa jpa, ManagerDao dao, RedisTemplate redisTemplate){
        this.dao = dao;
        this.jpa = jpa;
        this.redisTemplate = redisTemplate;
    }

    public R<Manager> login(Manager manager){
        LambdaQueryWrapper<Manager> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Manager::getManagerName,manager.getManagerName()).eq(Manager::getPassword,manager.getPassword());
        Manager manager1=this.getOne(queryWrapper);
        if(manager1!=null){
            if(manager1.getStatus()==0){
                return R.error("该账号已被禁用");
            }
            redisTemplate.opsForValue().set("manager",manager1.getId(),24, TimeUnit.HOURS);
            return R.success(manager1);
        }
        return R.error("登录失败");
    }

    public R<Manager> updateManager(Manager manager){
        int result = dao.updateById(manager);
        if (result >= 1){
            return R.success(dao.selectById(manager));
        }else
            return R.error("更新失败！");
    }

}
