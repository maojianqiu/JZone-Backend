package com.blog.admin.service;

import com.blog.admin.dto.BgmsBlogParam;
import com.blog.admin.dto.BgmsClassifyParam;
import com.blog.common.api.CommonResult;
import com.blog.mbg.model.BgmsBlog;
import com.blog.mbg.model.BgmsClassify;
import org.springframework.web.bind.annotation.PathVariable;

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
    public List<BgmsBlog> bloglist(String keyword, Integer pageSize, Integer pageNum);

    /*
     * 批量删除博文;
     * */
    public int blogdel(List<Long> ids);
}
