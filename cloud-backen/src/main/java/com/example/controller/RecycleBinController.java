package com.example.controller;

import com.example.domain.File;
import com.example.service.RecycleBinService;
import com.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recylebin")
public class RecycleBinController {


    @Autowired
    private final RecycleBinService recyclebinService;

    @Autowired
    public RecycleBinController(RecycleBinService recyclebinService){
        this.recyclebinService = recyclebinService;
    }

    /**
     * 回收站文件信息
     * @return
     */
    @CrossOrigin
    @GetMapping
    public R<List<File>> getAll(){
        return recyclebinService.getAll();
    }

    /**
     * 删除回收站文件
     * @param
     * @return
     */
    @CrossOrigin
    @PostMapping("/delete")
    public R<String> delete(@RequestBody File file) throws Exception {
        return recyclebinService.delete(file);
    }

    /**
     * 回收站清空
     * @return
     */
    @CrossOrigin
    @DeleteMapping
    public R<String> deleteAll(){
        return recyclebinService.deleteAll();
    }

    /**
     * 回收站文件恢复
     * @param file
     * @return
     */
    @CrossOrigin
    @PostMapping
    public R<String> restore(@RequestBody File file){
        return recyclebinService.restore(file);
    }
}
