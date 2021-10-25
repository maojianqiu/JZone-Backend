package com.blog.portal.dao;


import com.blog.mbg.model.BgmsBlogstat;
import com.blog.portal.dto.BgmsBlogParam;

import java.util.List;

/**
 * 搜索blog管理自定义Dao
 */
public interface BlogDao {

    /**
     * 获取指定ID的搜索商品
     */
    List<BgmsBlogParam> getAllBlogList();

    Integer freshBlogViews(List<BgmsBlogstat> lists);
}
