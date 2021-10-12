package com.blog.search.service.impl;

import com.blog.search.domain.EsBlog;
import com.blog.search.service.EsBlogService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 搜索博文管理Service实现类
 * 两种方式：
 * 1.EsBlogRepository
 * 2.ElasticsearchRestTemplate
 * Created by macro on 2018/6/19.
 */

@Service
public class EsBlogServiceImpl implements EsBlogService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


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
    public Page<EsBlog> search(String keyword, Long title, Long description , Long content, Integer pageNum, Integer pageSize, Integer sort) {
        String preTag = "<font color='#dd4b39'>";//google的色值
        String postTag = "</font>";
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("title").preTags(preTag).postTags(postTag);
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", title);

        // Query对象 建造者模式 其中的分页和排序同样看代码可知。
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(matchQueryBuilder).withFields("name", "desc").withPageable(pageable)
                .withHighlightBuilder(highlightBuilder).build();

        SearchHits<EsBlog> searchHits = elasticsearchRestTemplate.search(query, EsBlog.class);

        if(searchHits.getTotalHits()<=0){
            return new PageImpl<>(null,pageable,0);
        }
        List<EsBlog> searchProductList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(searchProductList,pageable,searchHits.getTotalHits());
    }

    @Override
    public Page<EsBlog> recommend(Long id, Integer pageNum, Integer pageSize) {
        return null;
    }
}
