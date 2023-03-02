package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.FileDao;
import com.example.dao.RecycleBinDao;
import com.example.dao.UserDao;
import com.example.domain.File;
import com.example.domain.RecycleBin;
import com.example.domain.User;
import com.example.service.FileService;
import com.example.service.RecycleBinService;
import com.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.List;

@Service
public class RecycleBinServiceImpl extends ServiceImpl<RecycleBinDao, RecycleBin> implements RecycleBinService {

    private final FileDao fileDao;
    private final FileService fileService;
    private final UserDao userDao;
    private final RecycleBinDao recycleBinDao;

    @Autowired
    public RecycleBinServiceImpl(FileDao fileDao,FileService fileService,UserDao userDao,RecycleBinDao recycleBinDao){
        this.fileDao = fileDao;
        this.fileService = fileService;
        this.userDao = userDao;
        this.recycleBinDao = recycleBinDao;
    }

    public R<List<File>> getAll(){
        List<File> file;
        LambdaQueryWrapper<File> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(File::getStatus,1);
        file=fileDao.selectList(queryWrapper);
        return R.success(file);
    }

    public R<String> delete(File file) throws Exception{
        fileDao.deleteById(file.getId());
        java.io.File temp = new java.io.File(file.getFile());
        FileInputStream fin = null;
        try {
            if (temp.exists()) {
                fin = new FileInputStream(temp);
                if (fin != null) {
                    fin.close();
                    fileService.delFile(temp);
                } else {
                    throw new Exception("需要转移的文件不存在!");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (fin != null) {
                fin.close();
            }
        }
        return R.success("删除成功");
    }

    public R<String> deleteAll(){
        fileDao.deleteAll();
        return R.success("删除成功！");
    }

    public R<String> restore(File file){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,file.getUserId());
        User user= userDao.selectOne(queryWrapper);
        user.setUsedSize(user.getUsedSize()+file.getFileSize());
        user.setRemainSize(user.getSpaceSize()-user.getUsedSize());
        userDao.updateById(user);
        file.setStatus(0);
        fileService.updateById(file);
        return R.success("文件恢复成功");
    }

}
