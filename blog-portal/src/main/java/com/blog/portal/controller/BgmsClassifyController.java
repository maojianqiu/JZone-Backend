package com.blog.portal.controller;

import com.blog.common.api.CommonPage;
import com.blog.common.api.CommonResult;
import com.blog.mbg.model.BgmsClassify;
import com.blog.portal.bo.MemberDetails;
import com.blog.portal.dto.BgmsClassifyParam;
import com.blog.portal.service.BgmsClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * description: BgmsClassifyController <br>
 * date: 2021/8/29 19:02 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */

@Controller
@Api(tags = "BgmsClassifyController",description = "后台分类管理")
@RequestMapping("/bmsclassify")
public class BgmsClassifyController {

    @Autowired
    BgmsClassifyService bgmsClassifyService;

    
    @ApiOperation(value = "新增分类")
    @RequestMapping(value = "/classifyadd", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult classifyAdd(Principal principal,
                                    @RequestBody BgmsClassifyParam bgmsClassifyParam){
        /**
         * 1.
         * */
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        MemberDetails memberDetails = (MemberDetails)authenticationToken.getPrincipal();
        bgmsClassifyParam.setUmsId(memberDetails.getId());

        int count = bgmsClassifyService.classifyAdd(bgmsClassifyParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "修改分类")
    @RequestMapping(value = "/classifyupdate", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult classifyUpdate(Principal principal,
            @RequestBody BgmsClassifyParam bgmsClassifyParam){
        if(principal==null){
            return CommonResult.unauthorized(null);
        }

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        MemberDetails memberDetails = (MemberDetails)authenticationToken.getPrincipal();
        if(memberDetails.getId() == bgmsClassifyParam.getUmsId()){
            return CommonResult.forbidden(null);
        }

        int count = bgmsClassifyService.classifyUpdate(bgmsClassifyParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

//    @ApiOperation(value = "获取分类详情")
//    @RequestMapping(value = "/classifyinfo/{classifyId}", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<BgmsClassify> classifyinfo(@PathVariable Long classifyId){
//
//        return null;
//    }

    @ApiOperation(value = "获取分类列表")
    @RequestMapping(value = "/classifylist", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<BgmsClassify>> classifylist(
            Principal principal,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        //获取当前登录对象的id，判断当前博文是否是当前对象的;
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        MemberDetails memberDetails = (MemberDetails)authenticationToken.getPrincipal();

        List<BgmsClassify> classifylist = bgmsClassifyService.classifylist(memberDetails.getId(),keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(classifylist));
    }

    @ApiOperation(value = "获取一篇博文分类列表")
    @RequestMapping(value = "/classifyInfo/{blogId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List> getClassifyInfo(@PathVariable Long blogId){
        //获取当前登录对象的id，判断当前博文是否是当前对象的;

        return CommonResult.success(bgmsClassifyService.classifylistByBlogId(blogId));
    }

    @ApiOperation(value = "批量删除分类")
    @RequestMapping(value = "/classifydel", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult classifydel(Principal principal,
                                    @RequestParam("ids") List<Long> ids){
        if(principal==null){
            return CommonResult.unauthorized(null);
        }

        int count = bgmsClassifyService.classifydel(ids);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
}
