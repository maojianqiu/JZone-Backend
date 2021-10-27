package com.blog.portal.controller;


import com.blog.common.api.CommonResult;
import com.blog.common.util.TencentCOSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@Controller
@RequestMapping("/api/cos")
public class TecentCOSController {

    @Autowired
    TencentCOSUtil tencentCOSUtil;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult upload(@RequestParam(value = "file") MultipartFile file){
        if (file == null){
            return CommonResult.failed("上传图片为空");
        }
        String uploadfile = tencentCOSUtil.uploadfile(file);
        return CommonResult.success(uploadfile);
//        return CommonResult.success("ceshi.jpg");
    }

    @RequestMapping(value = "/delload", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delload(@RequestParam(value = "url") String url){
        if (url == null){
            return CommonResult.failed("图片名称为空");
        }
        Boolean result = tencentCOSUtil.delload(url);
//        return CommonResult.success(uploadfile);
        ArrayList<String> s = new ArrayList<>();
        Iterator<String> s1= s.iterator();
        return CommonResult.success(result);

    }
}
