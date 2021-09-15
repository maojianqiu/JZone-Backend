package com.blog.admin.service;

import com.blog.admin.dto.BgmsClassifyParam;
import com.blog.common.api.CommonResult;
import com.blog.mbg.model.BgmsClassify;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BgmsClassifyService {

    /*
    * 新增分类
    * */
    public int classifyAdd(BgmsClassifyParam bgmsClassifyParam);

    /*
     * 修改分类
     * */
    public int classifyUpdate(BgmsClassifyParam bgmsClassifyParam);

    /*
     * 获取分类列表
     * */
    public List<BgmsClassify> classifylist(String keyword, Integer pageSize, Integer pageNum);

    /*
     * 获取一篇博文分类列表
     * */
    public List<Long> classifylistByBlogId(Long blogId);

    /*
     * 批量删除分类;
     * */
    public int classifydel(List<Long> ids);

    /*
     * 博文修改所属分类
     * 0 : 新增博文
     * 1 ：更新博文
     * */
    public int classifyBlogRelationUpdate(Integer state , Long blogId , List<Long> ids);


}
