package com.blog.portal.controller;

import com.blog.common.api.CommonPage;
import com.blog.common.api.CommonResult;
import com.blog.mbg.model.BgmsBlog;
import com.blog.mbg.model.BgmsTag;
import com.blog.portal.bo.MemberDetails;
import com.blog.portal.dto.BgmsBlogParam;
import com.blog.portal.dto.BgmsTagParam;
import com.blog.portal.service.BgmsBlogService;
import com.blog.portal.service.BgmsClassifyService;
import com.blog.portal.service.BgmsTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@Api(tags = "BgmsBlogController",description = "博主的博文管理")
@RequestMapping("/bmsblog")
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
    public CommonResult blogAdd(Principal principal,
            @RequestBody BgmsBlogParam bgmsBlogParam
    ){
        /*
        * 1.保存博文详情（内容）
        * 2.获取当前博文id，然后保存到 tag  blog_classify_relation 两个表中
        * */
        //获取当前登录对象的id，将他绑定到当前博文上;
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        MemberDetails memberDetails = (MemberDetails)authenticationToken.getPrincipal();
        bgmsBlogParam.setUmsId(memberDetails.getId());

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
    public CommonResult blogUpdate(Principal principal,
            @RequestBody BgmsBlogParam bgmsBlogParam

            ){
        /*
         * 1.更新博文详情（内容）
         * 2.然后保更新 tag  blog_classify_relation 两个表中
         * */
        //获取当前登录对象的id，判断当前博文是否是当前对象的;
        if(principal==null){
            return CommonResult.unauthorized(null);
        }

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        MemberDetails memberDetails = (MemberDetails)authenticationToken.getPrincipal();
        if(memberDetails.getId() != bgmsBlogParam.getUmsId()){
            return CommonResult.forbidden(null);
        }

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
    public CommonResult<BgmsBlogParam> bloginfo(
            Principal principal,
            @PathVariable Long blogId){
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        MemberDetails memberDetails = (MemberDetails)authenticationToken.getPrincipal();

        BgmsBlogParam bgmsBlogParam = bgmsBlogService.bloginfo(blogId);

        //如果当前登录人不是当前访问的博文的拥有者
        if(bgmsBlogParam.getUmsId() != memberDetails.getId()){
            CommonResult.forbidden("没有权限访问");
        }

        List<BgmsTag> bgmsTags = bgmsTagService.taglistByBlogId(blogId);
        List<BgmsTagParam> bgmsTagParamList = new ArrayList<>();
        for (BgmsTag tag: bgmsTags) {
            BgmsTagParam bgmsTagParam = new BgmsTagParam();
            BeanUtils.copyProperties(tag,bgmsTagParam);
            bgmsTagParamList.add(bgmsTagParam);
        }
        bgmsBlogParam.setTags(bgmsTagParamList);

        if(bgmsBlogParam != null){
            return CommonResult.success(bgmsBlogParam);
        }else {
            return CommonResult.failed();
        }

    }

    @ApiOperation(value = "获取博文列表")
    @RequestMapping(value = "/bloglist", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<BgmsBlog>> bloglist(
            Principal principal,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        //获取当前登录对象的id，判断当前博文是否是当前对象的;
        if(principal==null){
            return CommonResult.unauthorized(null);
        }

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        MemberDetails memberDetails = (MemberDetails)authenticationToken.getPrincipal();

        List<BgmsBlog> lists = bgmsBlogService.bloglist(memberDetails.getId(),keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(lists));
    }

    @ApiOperation(value = "删除单个博文")
    @RequestMapping(value = "/blogdel", method = RequestMethod.POST)
    @ResponseBody
    //@RequestParam：将请求参数绑定到你控制器的方法参数上（是springmvc中接收普通参数的注解）
    public CommonResult blogdel(
            Principal principal,
            @RequestParam("id") Long id){
        //获取当前登录对象的id，判断当前博文是否是当前对象的;
        if(principal==null){
            return CommonResult.unauthorized(null);
        }

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        MemberDetails memberDetails = (MemberDetails)authenticationToken.getPrincipal();

        int count = bgmsBlogService.blogdel(memberDetails.getId() , id);

        //说明当前博文和登陆人不一致，没有权限
        if(count == 0){
            return CommonResult.forbidden(null);
        }
        return CommonResult.success("已删除");
    }
}
