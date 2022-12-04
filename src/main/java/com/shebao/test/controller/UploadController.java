package com.shebao.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.shebao.test.util.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UploadController {

    @Autowired
    private MinioUtils minioUtils;

    @GetMapping("init")
    public String init() {
        return "file";
    }

    /**
     * 上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam(name = "file", required = false) MultipartFile file, HttpServletRequest request) {
        JSONObject res = null;
        try {
            res = minioUtils.uploadFile(file, "product");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code", 0);
            res.put("msg", "上传失败");
        }
        return res.toJSONString();
    }
}
