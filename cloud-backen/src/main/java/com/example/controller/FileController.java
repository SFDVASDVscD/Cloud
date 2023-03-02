package com.example.controller;

import com.example.domain.File;
import com.example.service.FileService;
import com.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    /**
     * 用户文件查看
     * 返回为list集合
     * @param type 为文件类型
     * @return
     */
    @CrossOrigin
    @GetMapping
    public R<List<File>> getAllFile(@RequestParam String[] type){

        return fileService.getAllFile(type);
    }

    /**
     * 用户上传文件
     * @param file
     * @return
     */
    @CrossOrigin
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        return fileService.upload(file);
    }

    /**
     * 用户下载文件
     * @param file
     * @return
     */
    @CrossOrigin
    @PostMapping("/download")
    public R<String> download(@RequestBody File file) throws Exception {
        return fileService.download(file);
    }

    /**
     * 用户删除文件
     * @param file
     * @return
     */
    @CrossOrigin
    @PostMapping("/delete")
    public R<String> delete(@RequestBody File file){
        return fileService.delete(file);
    }

    /**
     * 管理员删除用户文件
     * @param file
     * @return
     */
    @CrossOrigin
    @PostMapping("/manager/delete")
    public R<String> deleteByManager(@RequestBody File file) throws Exception {
        return fileService.deleteByManager(file);
    }

    @CrossOrigin
    @PostMapping("/browse")
    public R<String> browse(@RequestBody File file){
        return fileService.browse(file);
    }

    @CrossOrigin
    @GetMapping("/recentbrowse")
    public R<List<File>> recentBrowse(){
        return fileService.recentBrowse();
    }

}
