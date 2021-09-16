package com.blog.portal.service;

import com.blog.mbg.model.BgmsClassify;
import com.blog.portal.dto.BgmsClassifyParam;

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
    public List<BgmsClassify> classifylist(Long id,String keyword, Integer pageSize, Integer pageNum);

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
    public int classifyBlogRelationUpdate(Integer state, Long blogId, List<Long> ids);


}
