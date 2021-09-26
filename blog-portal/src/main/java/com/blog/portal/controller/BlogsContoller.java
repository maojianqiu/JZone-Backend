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
 * description: BlogsContoller <br>
 * date: 2021/9/22 7:07 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */

@Controller
@Api(tags = "BlogsContoller",description = "前台的博文管理")
@RequestMapping("/blogs")
public class BlogsContoller {
    @Autowired
    BgmsBlogService bgmsBlogService;
    @Autowired
    BgmsClassifyService bgmsClassifyService;
    @Autowired
    BgmsTagService bgmsTagService;

    @ApiOperation(value = "获取博文详情")
    @RequestMapping(value = "/bloginfo/{blogId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<BgmsBlogParam> bloginfo(@PathVariable Long blogId){
        /*
        注意，获取后，需要把隐私信息去掉，例如usmid
         */

        BgmsBlogParam bgmsBlogParam = bgmsBlogService.bloginfo(blogId);

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
        /*
        注意，获取后，需要把隐私信息去掉，例如usmid，即只传自己需要的数据
         */
        List<BgmsBlog> lists = bgmsBlogService.bloglist(null,keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(lists));
    }
}
