package com.blog.portal.service;

import com.blog.mbg.model.BgmsBlog;
import com.blog.portal.dto.BgmsBlogParam;

import java.util.List;

public interface BgmsBlogService {
    /*
     * 新增博文
     * */
    public Long blogAdd(BgmsBlogParam bgmsClassifyParam);

    /*
     * 修改博文
     * */
    public int blogUpdate(BgmsBlogParam bgmsClassifyParam);

    /*
     * 获取博文详情
     * */
    public BgmsBlog bloginfo(Long blogId);

    /*
     * 获取博文列表
     * */
    public List<BgmsBlog> bloglist(Long userId,String keyword, Integer pageSize, Integer pageNum);

    /*
     * 批量删除博文;
     * */
    public int blogdel(Long umsId,Long id);
}
