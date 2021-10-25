package com.blog.portal.service.impl;

import com.blog.common.service.RedisService;
import com.blog.mbg.mapper.BgmsBlogstatMapper;
import com.blog.mbg.mapper.UmsMemberMapper;
import com.blog.mbg.model.BgmsBlogstat;
import com.blog.mbg.model.BgmsBlogstatExample;
import com.blog.portal.service.BgmsBlogCacheService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BgmsBlogCacheServiceImpl implements BgmsBlogCacheService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private BgmsBlogstatMapper bgmsBlogstatMapper;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.isviews}")
    private Long REDIS_EXPIRE_IS_VIEWS;
    @Value("${redis.key.views}")
    private String REDIS_KEY_VIEWS;
    @Value("${redis.key.isviews}")
    private String REDIS_KEY_IS_VIEWS;

    @Override
    public Integer isViewAdd(Long blogId, String ip, Long umsId) {
        /**
         * 当前浏览IP是否增加浏览
         * 1.获取当前浏览 ip
         * 2.调用接口
         *      3.判断 key：views 总数是否存在
         *          是：跳过
         *          否：从数据库中取出导入，可能为零
         *      4.判断（时效内）当前浏览人是否存在
         *          是：跳过
         *          否：1.增加views，2.增加 isviews
         *  5.无论成功与否，当前都 setview++ ,只是显示
         */


        Set<String> s = redisService.getkeys(REDIS_KEY_IS_VIEWS+":*");





        String umscode = ip ;
        if (umsId != null) {
            umscode = umscode + umsId;
        }

        String viewsKey = REDIS_DATABASE + ":" + REDIS_KEY_VIEWS + ":" + blogId;
        String isviewsKey = REDIS_DATABASE + ":" + REDIS_KEY_IS_VIEWS + ":" + blogId + ":" + umscode;

        Boolean hasViews = redisService.hasKey(viewsKey);
        if(!hasViews){
            //从数据库中取出再 set
            BgmsBlogstatExample bgmsBlogstatExample = new BgmsBlogstatExample();
            BgmsBlogstatExample.Criteria criteria = bgmsBlogstatExample.createCriteria();
            criteria.andBlogIdEqualTo(blogId);

            List<BgmsBlogstat> lists = bgmsBlogstatMapper.selectByExample(bgmsBlogstatExample);
            redisService.set(viewsKey,lists.get(0).getViews());
        }

        Boolean hasIsViews = redisService.hasKey(isviewsKey);
        if(!hasIsViews){
            redisService.incr(viewsKey,1);
            redisService.set(isviewsKey,1,REDIS_EXPIRE_IS_VIEWS);
        }

        return null;
    }
}
