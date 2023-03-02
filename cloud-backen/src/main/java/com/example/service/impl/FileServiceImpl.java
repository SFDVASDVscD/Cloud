package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dao.UserDao;
import com.example.domain.File;
import com.example.domain.User;
import com.example.service.FileService;
import com.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.example.dao.FileDao;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl extends ServiceImpl<FileDao, File> implements FileService {

    @Value("${cloud.uploadpath}")
    private String uploadPath;

    @Value("${cloud.downloadpath}")
    private String downloadPath;
    @Autowired
    private final RedisTemplate redisTemplate;
    private final UserDao userDao;
    private final FileDao fileDao;

    @Autowired
    public FileServiceImpl(RedisTemplate redisTemplate, UserDao userDao, FileDao fileDao){
        this.redisTemplate = redisTemplate;
        this.userDao = userDao;
        this.fileDao = fileDao;
    }

    public void delFile(java.io.File file) throws Exception {
        if(!file.exists()) {
            throw new Exception("文件"+file.getName()+"不存在,请确认!");
        }
        if(file.isFile()){
            if(file.canWrite()){
                file.delete();
            }else{
                throw new Exception("文件"+file.getName()+"只读,无法删除,请手动删除!");
            }
        }else{
            throw new Exception("文件"+file.getName()+"不是一个标准的文件,有可能为目录,请确认!");
        }
    }

    public R<List<File>> fileList(Long id){
        LambdaQueryWrapper<File> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(File::getUserId,id).eq(File::getStatus,0);
        List<File> files = this.list(queryWrapper);
        return R.success(files);
    }

    public R<List<File>> getAllFile(String[] type){
        Object userid =redisTemplate.opsForValue().get("user");
        List<File> file;
        LambdaQueryWrapper<File> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(File::getStatus,0).eq(File::getUserId,userid);
        for (String s : type) {
            queryWrapper.eq(File::getFileType, s).or();
        }
        file=this.list(queryWrapper);
        return R.success(file);
    }

    public R<String> upload(MultipartFile file) throws IOException {
        File File=new File();
        Object userid =redisTemplate.opsForValue().get("user");
        String originalFilename = file.getOriginalFilename();
        String suffix= originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString()+suffix;

        java.io.File dir=new java.io.File(uploadPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File.setUserId((Long) userid);
        File.setFileName(originalFilename);
        File.setFile(uploadPath+fileName);
        File.setFileType(suffix);
        File.setFileSize(file.getSize());

        this.save(File);
        file.transferTo(new java.io.File(uploadPath+fileName));
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,userid);
        User user = userDao.selectOne(queryWrapper);
        if(user.getUsedSize()+file.getSize()>user.getSpaceSize()){
            return R.error("云盘空间已满");
        }
        user.setUsedSize(user.getUsedSize()+file.getSize());
        user.setRemainSize(user.getSpaceSize()-user.getUsedSize());
        userDao.updateById(user);
        return R.success("上传成功");
    }

    public R<String> download(File file) throws Exception{
        java.io.File dir = new java.io.File(downloadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        int byteread = 0;
        java.io.File oldFile = new java.io.File(file.getFile());
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            if (oldFile.exists()) {
                fin = new FileInputStream(oldFile);
                fout = new FileOutputStream(downloadPath+file.getFileName());
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

    public R<String> delete(File file){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,file.getUserId());
        User user=userDao.selectOne(queryWrapper);
        user.setUsedSize(user.getUsedSize()-file.getFileSize());
        user.setRemainSize(user.getSpaceSize()-user.getUsedSize());
        userDao.updateById(user);
        file.setStatus(1);
        fileDao.updateById(file);
        return R.success("删除成功");
    }

    public R<String> deleteByManager(File file) throws Exception{
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,file.getUserId());
        User user=userDao.selectOne(queryWrapper);
        user.setUsedSize(user.getUsedSize()-file.getFileSize());
        user.setRemainSize(user.getSpaceSize()-user.getUsedSize());
        userDao.updateById(user);
        fileDao.deleteById(file.getId());
        java.io.File temp = new java.io.File(file.getFile());
        FileInputStream fin = null;
        try {
            if (temp.exists()) {
                fin = new FileInputStream(temp);
                if (fin != null) {
                    fin.close();
                    this.delFile(temp);
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

    public R<String> browse( File file){
        if(!redisTemplate.hasKey("file"+file.getUserId())||redisTemplate.opsForValue().get("file"+file.getUserId())==null){
            redisTemplate.opsForValue().set("file"+file.getUserId(),null);
            List<File> files=new ArrayList<>();
            files.add(file);
            redisTemplate.opsForValue().set("file"+file.getUserId(), files);
        }
        else {
            List<File> files;
            files = (List<File>) redisTemplate.opsForValue().get("file"+file.getUserId());
            files.add(file);
            redisTemplate.opsForValue().set("file"+file.getUserId(), files);
        }
        return R.success(null);
    }

    public R<List<File>> recentBrowse(){
        Object userid =redisTemplate.opsForValue().get("user");
        List<File> files= (List<File>) redisTemplate.opsForValue().get("file"+userid);
        return R.success(files);
    }

}
