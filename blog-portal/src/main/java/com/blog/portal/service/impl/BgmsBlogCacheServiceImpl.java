package com.blog.portal.service.impl;

import com.blog.common.service.RedisService;
import com.blog.mbg.mapper.BgmsBlogLikesMapper;
import com.blog.mbg.mapper.BgmsBlogstatMapper;
import com.blog.mbg.mapper.UmsMemberMapper;
import com.blog.mbg.model.BgmsBlogLikes;
import com.blog.mbg.model.BgmsBlogLikesExample;
import com.blog.mbg.model.BgmsBlogstat;
import com.blog.mbg.model.BgmsBlogstatExample;
import com.blog.portal.dao.BlogDao;
import com.blog.portal.service.BgmsBlogCacheService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BgmsBlogCacheServiceImpl implements BgmsBlogCacheService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private BgmsBlogstatMapper bgmsBlogstatMapper;
    @Autowired
    private BgmsBlogLikesMapper bgmsBlogLikesMapper;
    @Autowired
    private BlogDao blogDao;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.isviews}")
    private Long REDIS_EXPIRE_IS_VIEWS;
    @Value("${redis.key.views}")
    private String REDIS_KEY_VIEWS;
    @Value("${redis.key.isviews}")
    private String REDIS_KEY_IS_VIEWS;
    @Value("${redis.key.likes}")
    private String REDIS_KEY_LIKES;
    @Value("${redis.key.islikes}")
    private String REDIS_KEY_IS_LIKES;

    @Override
    public Integer isViewAdd(Long blogId, String ip, Long umsId) {
        /**
         * 当前浏览IP是否增加浏览
         * 1.获取当前浏览 ip
         * 2.调用接口
         *      3.判断 String key：views:blogid 总数是否存在
         *          是：跳过
         *          否：从数据库中取出导入，可能为零
         *      4.判断 String key：views:blogid:umsid（时效内）当前浏览人是否存在
         *          是：跳过
         *          否：1.增加views，2.增加 isviews
         *  5.无论成功与否，当前都 setview++ ,只是显示
         */


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

    @Override
    public Integer freshBlogView() {
        String viewsKeybroad = REDIS_DATABASE + ":" + REDIS_KEY_VIEWS +":*" ;



        //1.拿到 redis 中所有的有 views 的 blogid 和 views
        Set<String> keys = redisService.getkeys(viewsKeybroad);

        List<BgmsBlogstat> lists = new ArrayList<>();

        Iterator<String> iterator = keys.iterator();
        for ( ; iterator.hasNext() ;){
            String key = iterator.next();

            Integer views = (Integer) redisService.get(key);
            String[] blogkey = key.split(":");

            BgmsBlogstat bgmsBlogstat = new BgmsBlogstat();
            bgmsBlogstat.setBlogId(Long.valueOf(blogkey[blogkey.length-1]));
            bgmsBlogstat.setViews(views);

            lists.add(bgmsBlogstat);
        }
        //2.批量更新到 mysql
        if(lists.size() == 0){
            return 0;
        }
        Integer count = blogDao.freshBlogViews(lists);


        return count;
    }

    @Override
    public Integer isCurUmsLike(Long blogId, Long umsId) {
        /**
         * 当前登录用户是否已点赞
         *
         * 1.获取当前登录对象的umsid
         * 2.调接口
         *      3.判断 String key:likes:blogid 总数是否存在
         *          是：跳过
         *          否：从数据库中取出导入，可能为零
         *
         *      4.判断当前对象 f = umsid 是否存在 SET k =  blogid 中
         *      是： 返值 1
         *      否： 返值 0
         * 5.接口返值
         *       值=1： 代表已点赞，1.setlikes(likes ++ )；2.setislike(true);
         *       值=0： 代表未点赞，1.setislike(false);
         */
        String likesKey = REDIS_DATABASE + ":" + REDIS_KEY_LIKES + ":" + blogId;
        String islikesKey = REDIS_DATABASE + ":" + REDIS_KEY_IS_LIKES + ":" + blogId ;

        Boolean hasLikes = redisService.hasKey(likesKey);
        if(!hasLikes){
            //从数据库中取出再 set
            BgmsBlogstatExample bgmsBlogstatExample = new BgmsBlogstatExample();
            BgmsBlogstatExample.Criteria criteria = bgmsBlogstatExample.createCriteria();
            criteria.andBlogIdEqualTo(blogId);

            List<BgmsBlogstat> lists = bgmsBlogstatMapper.selectByExample(bgmsBlogstatExample);
            redisService.set(likesKey,lists.get(0).getLikes());

            BgmsBlogLikesExample bgmsBlogLikesExample = new BgmsBlogLikesExample();
            BgmsBlogLikesExample.Criteria criteria2 = bgmsBlogLikesExample.createCriteria();
            criteria2.andBlogIdEqualTo(blogId);

            List<BgmsBlogLikes> lists2 = bgmsBlogLikesMapper.selectByExample(bgmsBlogLikesExample);
            String id = lists2.get(0).getUmsIds();
            if(id != null){
                String[] ids = id.split(":");
                Long[] umsids = new Long[ids.length];

                int i = 0 ;
                for (String s: ids) {
                    umsids[i] = Long.valueOf(s);
                    i++;
                }
                redisService.sAdd(islikesKey,umsids);
            }
        }

        Boolean isLikes = redisService.sIsMember(islikesKey , umsId);
        if(isLikes){
            return 1;
        }

        return 0;
    }

    @Override
    public Integer isCurUmsLikeAdd(Long blogId, Long umsId, Integer isCurLike) {
         /*
           1.掉接口（ 要么前端返回当前点赞状态，要么重新判断 1 0，若前端返回可能会有用户恶意点赞）
                2.若已点赞，则是取消点赞：
                    (1).String key:likes:blogid ,--;
                    (2).SET key:islikes:blogid, 去除 field:umsid
                 若未点赞，则是点赞
                    (1).String key:likes:blogid ,++;
                    (2).SET key:islikes:blogid, 添加 field:umsid
                 若操作成功，反值：1
                 若操作失败，反值：0
            3.接口返值
         *       值=1： 代表成功
         *       值=0： 代表失败
         */
         Integer result = 0 ;
        String likesKey = REDIS_DATABASE + ":" + REDIS_KEY_LIKES + ":" + blogId;
        String islikesKey = REDIS_DATABASE + ":" + REDIS_KEY_IS_LIKES + ":" + blogId ;


         if(isCurLike == 1){
             Long likedecr = redisService.decr(likesKey,1);
             Long islikedel = redisService.sRemove(islikesKey,umsId);
             result =1;

         }else {
            Long likeinc = redisService.incr(likesKey,1);
            Long islikeadd = redisService.sAdd(islikesKey,umsId);
            result =1;
         }

        return result;
    }

    @Override
    public Integer freshBlogLike() {
        /**
         * 1.拿到 redis 中所有的 islikes blogid列表
         * 2.通过 blogid 拿到所有的 member 列表即umsid
         * 3.并通过列表 获取 likes 总数
         * 4.保存到 bgms_blogstat ， bgms_like_list
         */

//        String likesKeyBroad = REDIS_DATABASE + ":" + REDIS_KEY_LIKES ;
        String islikesKeyBroad = REDIS_DATABASE + ":" + REDIS_KEY_IS_LIKES ;

        Set<String> keys = redisService.getkeys(islikesKeyBroad+":*");

        List<BgmsBlogstat> listlikes = new ArrayList<>(); //blogstat likes
        List<BgmsBlogLikes> listumss = new ArrayList<>(); // blog_likes

        Iterator<String> iterator = keys.iterator();
        for ( ; iterator.hasNext() ;){
            String key = iterator.next();
            String[] blogkey = key.split(":");
            Long blogid = Long.valueOf(blogkey[blogkey.length-1]);
            BgmsBlogstat bgmsBlogstat = new BgmsBlogstat();
            BgmsBlogLikes bgmsBlogLikes = new BgmsBlogLikes();

            Set<Object> likeMembers =  redisService.sMembers(key);
            Integer likeumsSize = likeMembers.size();
            bgmsBlogstat.setBlogId(blogid);
            bgmsBlogstat.setLikes(likeumsSize);

            String[] umsids = new String[likeumsSize];
            Iterator likeiter = likeMembers.iterator();
            int i = 0;
            for(;likeiter.hasNext();){
                umsids[i] = String.valueOf(likeiter.next()) ;
                i++;
            }
            bgmsBlogLikes.setBlogId(blogid);
            bgmsBlogLikes.setUmsIds(String.join(":", umsids));

            listumss.add(bgmsBlogLikes);
            listlikes.add(bgmsBlogstat);
        }
        if(listumss.size() == 0){
            return 0;
        }

        //2.批量更新到 blogstats
        Integer countlikes = blogDao.freshBlogLikes(listlikes);

        //3.批量更新到 blog_like
        Integer countumslike = blogDao.freshBlogUmsLikes(listumss);

        return countlikes;
    }


}
