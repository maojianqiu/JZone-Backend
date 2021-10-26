package com.blog.portal.dao;


import com.blog.mbg.model.BgmsBlogLikes;
import com.blog.mbg.model.BgmsBlogstat;
import com.blog.portal.dto.BgmsBlogParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 搜索blog管理自定义Dao
 */
public interface BlogDao {

    /**
     * 获取指定ID的搜索商品
     */
    List<BgmsBlogParam> getAllBlogList();

    Integer freshBlogViews(List<BgmsBlogstat> lists);

    Integer freshBlogLikes(List<BgmsBlogstat> lists);

    Integer freshBlogUmsLikes(List<BgmsBlogLikes> lists);
}
