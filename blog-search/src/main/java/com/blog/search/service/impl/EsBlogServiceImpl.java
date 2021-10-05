package com.blog.search.service.impl;

import com.blog.search.domain.EsBlog;
import com.blog.search.service.EsBlogService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 搜索博文管理Service实现类
 * 两种方式：
 * 1.EsBlogRepository
 * 2.ElasticsearchRestTemplate
 * Created by macro on 2018/6/19.
 */

@Service
public class EsBlogServiceImpl implements EsBlogService {
    
    
    
    @Override
    public int importAll() {
        return 0;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public EsBlog create(Long id) {
        return null;
    }

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    public Page<EsBlog> search(String keyword, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public Page<EsBlog> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {
        return null;
    }

    @Override
    public Page<EsBlog> recommend(Long id, Integer pageNum, Integer pageSize) {
        return null;
    }
}
