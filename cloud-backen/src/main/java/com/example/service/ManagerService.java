package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.Manager;
import com.example.utils.R;

public interface ManagerService extends IService<Manager> {

    R<Manager> login(Manager manager);

    R<Manager> updateManager(Manager manager);

}
