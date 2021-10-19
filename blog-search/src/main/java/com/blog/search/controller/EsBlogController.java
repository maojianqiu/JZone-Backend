package com.blog.search.controller;


import com.blog.common.api.CommonPage;
import com.blog.common.api.CommonResult;
import com.blog.search.domain.EsBlog;
import com.blog.search.service.EsBlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 搜索博文管理Controller
 */
@Controller
@Api(tags = "EsBLOGController", description = "搜索博文管理")
@RequestMapping("/esBlog")
public class EsBlogController {

    @Autowired
    private EsBlogService esBlogService;

    @ApiOperation(value = "导入所有数据库中blog到ES")
    @RequestMapping(value = "/importAll", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Integer> importAllList() {
        int count = esBlogService.importAll();
        return CommonResult.success(count);
    }


    @ApiOperation(value = "根据id删除商品")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Object> delete(@PathVariable Long id) {
        esBlogService.delete(id);
        return CommonResult.success(null);
    }

    /**
     * 撤销后，或删除，或不通过后 需要撤销 ES中的，不让前台搜索到
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据id批量删除商品")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> delete(@RequestParam("ids") List<Long> ids) {
        esBlogService.delete(ids);
        return CommonResult.success(null);
    }

    /**
     * 1.审核通过后加入 ES 中方便后续检索
     * 2.定时添加
     *
     * 需要平台管理员的权限才可以新增和删除
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id创建商品")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<EsBlog> create(@PathVariable Long id) {
        EsBlog esProduct = esBlogService.create(id);
        if (esProduct != null) {
            return CommonResult.success(esProduct);
        } else {
            return CommonResult.failed();
        }
    }


    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<EsBlog>> search(@RequestParam(required = false) String keyword,
                                                   @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                   @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsBlog> esBlogPage = esBlogService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esBlogPage));
    }

    @ApiOperation(value = "综合搜索、筛选、排序")
    @ApiImplicitParam(name = "sort", value = "排序字段:0->按相关度；1->按新品；2->按销量；3->价格从低到高；4->价格从高到低",
            defaultValue = "0", allowableValues = "0,1,2,3,4", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<EsBlog>> search(@RequestParam(required = false) String keyword,
                                                   @RequestParam(required = false) String title,
                                                   @RequestParam(required = false) String der,
                                                   @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                   @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                   @RequestParam(required = false, defaultValue = "0") Integer sort) {
        Page<EsBlog> esProductPage = esBlogService.search(keyword, title, der, pageNum, pageSize, sort);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }
}
