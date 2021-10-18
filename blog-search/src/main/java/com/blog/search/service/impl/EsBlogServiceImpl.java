package com.blog.search.service.impl;

import com.blog.search.dao.EsBlogDao;
import com.blog.search.domain.EsBlog;
import com.blog.search.repository.EsBlogRepository;
import com.blog.search.service.EsBlogService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
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
    public Page<EsBlog> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //分页
        nativeSearchQueryBuilder.withPageable(pageable);


        //搜索
        if (StringUtils.isEmpty(keyword)) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        } else {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();

            //matchQuery("filedname","value")匹配单个字段，匹配字段名为filedname,值为value的文档
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("title", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(10)));
//            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("description", keyword),
//                    ScoreFunctionBuilders.weightFactorFunction(5)));
//            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("content", keyword),
//                    ScoreFunctionBuilders.weightFactorFunction(2)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }

        nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
//        LOGGER.log(Level.parse("DSL:{}"), searchQuery.getQuery().toString());
//        System.out.println("DSL:"+searchQuery.getQuery().toString());
        SearchHits<EsBlog> searchHits = elasticsearchRestTemplate.search(searchQuery, EsBlog.class);
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
