package com.blog.portal.controller;

import com.blog.common.api.CommonPage;
import com.blog.common.api.CommonResult;
import com.blog.common.util.IpUtil;
import com.blog.mbg.model.BgmsBlog;
import com.blog.mbg.model.BgmsTag;
import com.blog.portal.bo.MemberDetails;
import com.blog.portal.dto.BgmsBlogParam;
import com.blog.portal.dto.BgmsTagParam;
import com.blog.portal.service.BgmsBlogCacheService;
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

import javax.servlet.http.HttpServletRequest;
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
    BgmsBlogCacheService bgmsBlogCacheService;
    @Autowired
    BgmsClassifyService bgmsClassifyService;
    @Autowired
    BgmsTagService bgmsTagService;

    @ApiOperation(value = "获取博文详情")
    @RequestMapping(value = "/bloginfo/{blogId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<BgmsBlogParam> bloginfo(
            @PathVariable Long blogId ,
            Principal principal,
            HttpServletRequest request){
        /*
        注意，获取后，需要把隐私信息去掉，例如usmid，
        若 blog 不是当前登录账号的，并且不是已发布状态，需要返回 404，
        若是当前账号的，直接正常显示
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


        /**
         * 当前浏览IP是否增加浏览
         * 1.获取当前浏览 ip/umsid
         * 2.调用接口 （blogid/ip/umsid）
         *      3.判断 key：views 总数是否存在
         *          是：跳过
         *          否：从数据库中取出导入，可能为零
         *      4.判断（时效内）当前浏览人是否存在
         *          是：跳过
         *          否：增加
         *  5.无论成功与否，当前都 setview++ ,只是显示
         */
        //获取IP地址
        String ipAddress = IpUtil.getIpAddr(request);

        Long umsId = null;
        if(principal != null ){
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
            MemberDetails memberDetails = (MemberDetails)authenticationToken.getPrincipal();
            umsId= memberDetails.getId();
        }


        bgmsBlogCacheService.isViewAdd(blogId , ipAddress , umsId);
        bgmsBlogParam.setViews(bgmsBlogParam.getViews()+1);


        /**
         * 当前登录用户是否已点赞
         *
         * 1.获取当前登录对象的umsid
         * 2.调接口
         *      3.判断当前对象 f = umsid 是否存在 SET k =  blogid 中
         *      是： 返值 1
         *      否： 返值 0
         * 4.接口返值
         *       值=1： 代表已点赞，1.setlikes(likes ++ )；2.setislike(1);
         *       值=0： 代表未点赞，1.setislike(0);
         */



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
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        /*
        注意，获取后，需要把隐私信息去掉，，即只传自己需要的数据
         */
        List<BgmsBlog> lists = bgmsBlogService.bloglist(null,100,keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(lists));
    }

    @ApiOperation(value = "获取博文列表和博文发布者")
    @RequestMapping(value = "/viewbloglist", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<BgmsBlogParam>> viewbloglist(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        /*
        注意，获取后，需要把隐私信息去掉，，即只传自己需要的数据
         */
        List<BgmsBlogParam> lists = bgmsBlogService.viewbloglist(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(lists));
    }


    @ApiOperation(value = "通过userID获取博文列表：博客主页")
    @RequestMapping(value = "/blogViewListByUserID/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<BgmsBlog>> blogViewListByUserID(
            @PathVariable Long id,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        /*
        注意，获取后，需要把隐私信息去掉，，即只传自己需要的数据
         */
        List<BgmsBlog> lists = bgmsBlogService.bloglist(id,2,keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(lists));
    }

    @ApiOperation(value = "通过classID获取博文列表：博客主页")
    @RequestMapping(value = "/blogViewListByClassyID/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<BgmsBlog>> blogViewListByClassyID(
            @PathVariable Long id,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        /*
        注意，获取后，需要把隐私信息去掉，，即只传自己需要的数据
         */
        List<BgmsBlog> lists = bgmsBlogService.bloglist(id,2,keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(lists));
    }


    @ApiOperation(value = "TEST 测试持久化 views")
    @RequestMapping(value = "/testfreshview", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<String> testfreshview(
            ){

        bgmsBlogCacheService.freshBlogView();
        return null;
    }
}
