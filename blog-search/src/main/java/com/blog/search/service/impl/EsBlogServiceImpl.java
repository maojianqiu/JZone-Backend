package com.blog.search.service.impl;

import com.blog.search.dao.EsBlogDao;
import com.blog.search.domain.EsBlog;
import com.blog.search.repository.EsBlogRepository;
import com.blog.search.service.EsBlogService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

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
    private EsBlogDao esBlogDao;
    @Autowired
    private EsBlogRepository esBlogRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    
    @Override
    public int importAll() {

        List<EsBlog> esBlogList = esBlogDao.getAllEsBlogList(null);
//        for (EsBlog e:
//             esBlogList) {
//            System.out.println(e.toString());
//        }
//        return 0;

        Iterable<EsBlog> esBlogIterable = esBlogRepository.saveAll(esBlogList);
        Iterator<EsBlog> iterator = esBlogIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        esBlogRepository.deleteById(id);
    }

    @Override
    public EsBlog create(Long id) {
        EsBlog result = null;
        List<EsBlog> esBlogList = esBlogDao.getAllEsBlogList(id);
        if (esBlogList.size() > 0) {
            EsBlog esProduct = esBlogList.get(0);
            result = esBlogRepository.save(esProduct);
        }
        return result;
    }

    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<EsBlog> esBlogList = new ArrayList<>();
            for (Long id : ids) {
                EsBlog esBlog = new EsBlog();
                esBlog.setId(id);
                esBlogList.add(esBlog);
            }
            esBlogRepository.deleteAll(esBlogList);
        }
    }


    @Override
    public Page<EsBlog> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esBlogRepository.findByTitleOrDescriptionOrContent(keyword, keyword, keyword, pageable);
    }

    @Override
    public Page<EsBlog> search(String keyword,  String title, String description, Integer pageNum, Integer pageSize, Integer sort) {

        String preTag = "<font color='#dd4b39'>";//google的色值
        String postTag = "</font>";
        HighlightBuilder.Field titlehighlightBuilder = new HighlightBuilder.Field("title").preTags("<span style='color:red'>").postTags("</span>").requireFieldMatch(false);
        HighlightBuilder.Field descriptionhighlightBuilder = new HighlightBuilder.Field("description").preTags("<span style='color:red'>").postTags("</span>").requireFieldMatch(false);
        HighlightBuilder.Field[] ary = new HighlightBuilder.Field[2];
        ary[0] = titlehighlightBuilder;
        ary[1] = descriptionhighlightBuilder;
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        NativeSearchQuery query = null;

        //搜索
        if (StringUtils.isEmpty(keyword)) {
            query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchAllQuery())
                    .withHighlightFields(ary)
                    .withPageable(pageable)
                    .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                    .withSort(SortBuilders.fieldSort("updateTime").order(SortOrder.DESC))
                    .build();
        } else {

            MatchQueryBuilder titlematchQueryBuilder = new MatchQueryBuilder("title", keyword).boost(6.0f);
            MatchQueryBuilder descriptionmatchQueryBuilder = new MatchQueryBuilder("description", keyword).boost(1.0f);
            // Query对象 建造者模式 其中的分页和排序同样看代码可知。
            query = new NativeSearchQueryBuilder()
                    .withQuery(titlematchQueryBuilder)
                    .withQuery(descriptionmatchQueryBuilder)
//                    .withFields("name", "desc")
                    .withHighlightFields(ary)
                    .withPageable(pageable)
                    .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                    .withSort(SortBuilders.fieldSort("updateTime").order(SortOrder.DESC))
                    .build();
        }

        SearchHits<EsBlog> searchHits = elasticsearchRestTemplate.search(query, EsBlog.class);
        if(searchHits.getTotalHits()<=0){
            return new PageImpl<>(null,pageable,0);
        }
        List<EsBlog> searchProductList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

        int i = 0;
        for (SearchHit s : searchHits) {
            searchProductList.get(i).setTitle(String.join("", s.getHighlightField("title")));
            searchProductList.get(i).setDescription(String.join("", s.getHighlightField("description")));
            i++;
        }

        return new PageImpl<>(searchProductList,pageable,searchHits.getTotalHits());
    }

    @Override
    public Page<EsBlog> recommend(Long id, Integer pageNum, Integer pageSize) {
        return null;
    }
}
