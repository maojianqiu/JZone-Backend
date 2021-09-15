package com.blog.admin.controller;

import com.blog.admin.dto.BgmsBlogParam;
import com.blog.admin.dto.BgmsTagParam;
import com.blog.admin.service.BgmsBlogService;
import com.blog.admin.service.BgmsClassifyService;
import com.blog.admin.service.BgmsTagService;
import com.blog.common.api.CommonPage;
import com.blog.common.api.CommonResult;
import com.blog.mbg.model.BgmsBlog;
import com.blog.mbg.model.BgmsTag;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * description: BgmsController <br>
 * date: 2021/8/29 18:33 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */

@Controller
@Api(tags = "BgmsBlogController",description = "后台博文管理")
@RequestMapping("/bgmsblog")
public class BgmsBlogController {

    @Autowired
    BgmsBlogService bgmsBlogService;
    @Autowired
    BgmsClassifyService bgmsClassifyService;
    @Autowired
    BgmsTagService bgmsTagService;


    @ApiOperation(value = "新增博文")
    @RequestMapping(value = "/blogadd", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult blogAdd(
            @RequestBody BgmsBlogParam bgmsBlogParam
//            ,@RequestBody BgmsTagParam bgmsTagParam
//            ,@RequestParam("classifyIds") List<Long> classifyIds
    ){
        /*
        * 1.保存博文详情（内容）
        * 2.获取当前博文id，然后保存到 tag  blog_classify_relation 两个表中
        * */

        Long blogid = bgmsBlogService.blogAdd(bgmsBlogParam);
        if (!bgmsBlogParam.getTags().isEmpty()){
            bgmsTagService.tagAdd(blogid , bgmsBlogParam.getTags());
        }
        if (!bgmsBlogParam.getClassifies().isEmpty()){
            bgmsClassifyService.classifyBlogRelationUpdate( 0 , blogid,bgmsBlogParam.getClassifies());
        }

        if (blogid != null ) {
            return CommonResult.success(blogid.toString());
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "修改博文")
    @RequestMapping(value = "/blogupdate", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult blogUpdate(
            @RequestBody BgmsBlogParam bgmsBlogParam
//            @RequestBody BgmsTagParam bgmsTagParam,
//            @RequestParam("classifyIds") List<Long> classifyIds
            ){
        /*
         * 1.更新博文详情（内容）
         * 2.然后保更新 tag  blog_classify_relation 两个表中
         * */

        int blogid = bgmsBlogService.blogUpdate(bgmsBlogParam);
        bgmsTagService.tagUpdate(bgmsBlogParam.getId() , bgmsBlogParam.getTags());
        bgmsClassifyService.classifyBlogRelationUpdate( 1 , bgmsBlogParam.getId(),bgmsBlogParam.getClassifies());

        if (blogid != 0 ) {
            return CommonResult.success(blogid);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "获取博文详情")
    @RequestMapping(value = "/bloginfo/{blogId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<BgmsBlogParam> bloginfo(@PathVariable Long blogId){
        BgmsBlog bgmsBlog = bgmsBlogService.bloginfo(blogId);
        BgmsBlogParam bgmsBlogParam = new BgmsBlogParam();
        BeanUtils.copyProperties(bgmsBlog , bgmsBlogParam);

        List<BgmsTag> bgmsTags = bgmsTagService.taglistByBlogId(blogId);
        List<BgmsTagParam> bgmsTagParamList = new ArrayList<>();
        for (BgmsTag tag: bgmsTags) {
            BgmsTagParam bgmsTagParam = new BgmsTagParam();
            BeanUtils.copyProperties(tag,bgmsTagParam);
            bgmsTagParamList.add(bgmsTagParam);
        }
        bgmsBlogParam.setTags(bgmsTagParamList);

        if(bgmsBlog != null){
            return CommonResult.success(bgmsBlogParam);
        }else {
            return CommonResult.failed();
        }

    }

    @ApiOperation(value = "获取博文列表")
    @RequestMapping(value = "/bloglist", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<BgmsBlog>> bloglist(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        List<BgmsBlog> lists = bgmsBlogService.bloglist(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(lists));
    }

    @ApiOperation(value = "批量删除博文")
    @RequestMapping(value = "/blogdel", method = RequestMethod.POST)
    @ResponseBody
    //@RequestParam：将请求参数绑定到你控制器的方法参数上（是springmvc中接收普通参数的注解）
    public CommonResult<BgmsBlog> blogdel(@RequestParam("ids") List<Long> ids){

        return null;
    }
}
