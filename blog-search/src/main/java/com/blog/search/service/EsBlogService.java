package com.blog.search.service;

import com.blog.search.domain.EsBlog;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 搜索博文管理Service
 */
public interface EsBlogService {

    /**
     * 从数据库中导入所有博文到ES
     */
    int importAll();

    /**
     * 根据id删除博文
     */
    void delete(Long id);

    /**
     * 根据id创建博文
     */
    EsBlog create(Long id);

    /**
     * 批量删除博文
     */
    void delete(List<Long> ids);

    /**
     * 根据关键字搜索名称或者副标题
     */
    Page<EsBlog> search(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据关键字搜索名称或者副标题复合查询
     */
    Page<EsBlog> search(String keyword, String title, String der, Integer pageNum, Integer pageSize,Integer sort);

    /**
     * 根据博文id推荐相关博文
     */
    Page<EsBlog> recommend(Long id, Integer pageNum, Integer pageSize);


}
