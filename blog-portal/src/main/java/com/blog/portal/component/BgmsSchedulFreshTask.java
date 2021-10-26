package com.blog.portal.component;

import com.blog.common.service.RedisService;
import com.blog.mbg.mapper.BgmsBlogstatMapper;
import com.blog.portal.service.BgmsBlogCacheService;
import com.blog.portal.service.impl.BgmsBlogCacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

/**
 * description: BgmsSchedulFreshTask 博文浏览量。点赞量定时刷新任务 <br>
 * date: 2021/10/25 20:17 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */

@Component
public class BgmsSchedulFreshTask {

    @Autowired
    private BgmsBlogCacheService bgmsBlogCacheService;

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每4分钟扫描一次redis，并持久化到mysql
     */
    @Scheduled(cron = "0 0/4 * ? * ?")
    private void freshBlogView() {
        Integer count = bgmsBlogCacheService.freshBlogView();

        System.out.println(new Date() + " --------------------------- ：已将redis里的博文view持久化到mysql中，数量： "+count+" 条");
    }

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每4分钟扫描一次redis，并持久化到mysql
     *
     *
     * 点赞持久化
     * 注意：先获取 isLikes SET 列表，拿到现在最新的点赞列表，从点赞列表中获取现有的点赞数，然后分别保存到两个表中
     *  如果先获取 likes 总数再获取列表数，这之间可能会新增点赞数和用户
     *
     *  要保证 ，mysql 中存储的总数和用户列表数量相等
     */
    @Scheduled(cron = "0 2/4 * ? * ?")
    private void freshBlogLike() {
        Integer count = bgmsBlogCacheService.freshBlogView();

        System.out.println(new Date() + " --------------------------- ：将redis里的博文like持久化到mysql中，数量： "+count+" 条");
    }
}
