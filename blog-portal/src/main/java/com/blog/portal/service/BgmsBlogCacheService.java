package com.blog.portal.service;

import com.blog.mbg.model.BgmsBlog;
import com.blog.portal.dto.BgmsBlogParam;

import java.util.List;

public interface BgmsBlogCacheService {

    /**
     * 增加浏览量判断
     */
    public Integer isViewAdd(Long blogId, String ip, Long umsId);


}
