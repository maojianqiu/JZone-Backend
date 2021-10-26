package com.blog.portal.service;

import com.blog.mbg.model.BgmsBlog;
import com.blog.portal.dto.BgmsBlogParam;

import java.util.List;

public interface BgmsBlogCacheService {

    /**
     * 增加浏览量判断
     */
    public Integer isViewAdd(Long blogId, String ip, Long umsId);


    /**
     * 从redis中持久化 view 总数到mysql
     */
    public Integer freshBlogView();

    /**
     * 判断当前登录用户是否点赞过当前博文
     */
    public Integer isCurUmsLike(Long blogId, Long umsId);

    /**
     * 当前登录用户点赞或取消点赞当前博文
     */
    public Integer isCurUmsLikeAdd(Long blogId, Long umsId , Integer isCurLike);


    /**
     * 从redis中持久化 like 总数和用户列表数 到mysql
     */
    public Integer freshBlogLike();


}
