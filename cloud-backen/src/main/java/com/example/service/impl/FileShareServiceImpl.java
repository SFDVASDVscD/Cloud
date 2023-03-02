package com.example.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.FileDao;
import com.example.dao.FileShareDao;
import com.example.domain.File;
import com.example.domain.Fileshare;
import com.example.service.FileShareService;
import com.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

@Service
public class FileShareServiceImpl extends ServiceImpl<FileShareDao, Fileshare> implements FileShareService {

    @Value("${cloud.downloadpath}")
    private String downloadPath;

    private final FileDao fileDao;

    @Autowired
    public FileShareServiceImpl(FileDao fileDao){
        this.fileDao = fileDao;
    }

    public R<String> share(File file){
        LambdaQueryWrapper<Fileshare> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Fileshare::getFileId,file.getId());
        if(this.getOne(queryWrapper)==null){
            LocalDateTime dateTime=LocalDateTime.now();
            Fileshare fileshare=new Fileshare();
            fileshare.setFileId(file.getId());
            fileshare.setShareUrl("http://localhost:8080/#/showshare?id="+file.getId());
            fileshare.setShareTime(dateTime);
            fileshare.setAccessRead(1);
            fileshare.setAccessWrite(1);
            this.save(fileshare);
        }
        else{
            Fileshare fileshare= this.getOne(queryWrapper);
            LocalDateTime dateTime=LocalDateTime.now();
            fileshare.setFileId(file.getId());
            fileshare.setShareUrl("http://localhost:8080/#/showshare?id="+file.getId());
            fileshare.setShareTime(dateTime);
            fileshare.setAccessRead(1);
            fileshare.setAccessWrite(1);
            this.updateById(fileshare);
        }

        return R.success("http://localhost:8080/#/showshare?id="+file.getId());
    }

    public R<String> getfile(Fileshare fileshare){
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Fileshare> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Fileshare::getFileId,fileshare.getFileId());
        Fileshare fileshare1=this.getOne(queryWrapper);
        LocalDateTime end = fileshare1.getShareTime();
        Duration duration = Duration.between(now, (Temporal) end);
        long days = duration.toDays(); //相差的天数
        if(days>3){
            return R.error("文件已过期");
        }
        File file=fileDao.selectById(fileshare1.getFileId());
        return R.success(file.getFile());
    }

    public R<String> download(File file) throws Exception{
        java.io.File dir = new java.io.File(downloadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        int byteread = 0;
        File file1=fileDao.selectById(file.getId());
        java.io.File oldFile = new java.io.File(file1.getFile());
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            if (oldFile.exists()) {
                fin = new FileInputStream(oldFile);
                fout = new FileOutputStream(downloadPath+file1.getFileName());
                byte[] buffer = new byte[1444];
                while ((byteread = fin.read(buffer)) != -1) {
                    fout.write(buffer, 0, byteread);
                }
                if (fin != null) {
                    fin.close();
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
        return R.success("下载成功");
    }

}
