package com.blog.search.repository;


import com.blog.search.domain.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 搜索blogES操作类
 * Created by macro on 2018/6/19.
 */
public interface EsBlogRepository  extends ElasticsearchRepository<EsBlog, Long> {


    /**
     *
     * @param title
     * @param description
     * @param content
     * @param page
     * @return
     */
    Page<EsBlog> findByTitleOrDescriptionOrContent(String title, String description, String content, Pageable page);

}
