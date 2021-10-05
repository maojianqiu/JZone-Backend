package com.blog.search.dao;


import com.blog.search.domain.EsBlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 搜索blog管理自定义Dao
 */
public interface EsBlogDao {

    /**
     * 获取指定ID的搜索商品
     */
    List<EsBlog> getAllEsBlogList(@Param("id") Long id);
}
