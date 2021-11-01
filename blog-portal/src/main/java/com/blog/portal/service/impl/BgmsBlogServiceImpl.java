package com.blog.portal.service.impl;

import com.blog.mbg.mapper.BgmsBlogLikesMapper;
import com.blog.mbg.mapper.BgmsBlogMapper;
import com.blog.mbg.mapper.BgmsBlogstatMapper;
import com.blog.mbg.model.*;
import com.blog.portal.dao.BgmsBlogTagClassifyDao;
import com.blog.portal.dao.BlogDao;
import com.blog.portal.dto.BgmsBlogParam;
import com.blog.portal.service.BgmsBlogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * description: BgmsBlogServiceImpl <br>
 * date: 2021/8/30 6:35 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */

@Service
public class BgmsBlogServiceImpl implements BgmsBlogService {
    @Autowired
    BgmsBlogMapper bgmsBlogMapper;
    @Autowired
    BgmsBlogTagClassifyDao bgmsBlogTagClassifyDao;
    @Autowired
    BlogDao blogDao;
    @Autowired
    BgmsBlogstatMapper bgmsBlogstatMapper;
    @Autowired
    BgmsBlogLikesMapper bgmsBlogLikesMapper;
    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public Long blogAdd(BgmsBlogParam bgmsClassifyParam) {

        BgmsBlog bgmsBlog = new BgmsBlog();
        BeanUtils.copyProperties(bgmsClassifyParam,bgmsBlog);
        bgmsBlog.setCreateTime(new Date());
        bgmsBlog.setUpdateTime(new Date());
        int count = bgmsBlogMapper.insertSelective(bgmsBlog);

        BgmsBlogstat bgmsBlogstat = new BgmsBlogstat();
        bgmsBlogstat.setBlogId(bgmsBlog.getId());
        bgmsBlogstat.setLikes(0);
        bgmsBlogstat.setViews(0);
        int count2 = bgmsBlogstatMapper.insertSelective(bgmsBlogstat);

        BgmsBlogLikes bgmsBlogLikes = new BgmsBlogLikes();
        bgmsBlogLikes.setBlogId(bgmsBlog.getId());
        bgmsBlogLikes.setUpdateTime(new Date());
        int count3 = bgmsBlogLikesMapper.insertSelective(bgmsBlogLikes);

        if (count > 0 ) {
            System.out.println("BgmsBlogServiceImpl--"+bgmsBlog.getId());
            return bgmsBlog.getId();
        } else {
            return null;
        }
    }

    @Override
    public int blogUpdate(BgmsBlogParam bgmsClassifyParam) {
        BgmsBlog bgmsBlog = new BgmsBlog();
        BeanUtils.copyProperties(bgmsClassifyParam,bgmsBlog);
        bgmsBlog.setUpdateTime(new Date());
        int count = bgmsBlogMapper.updateByPrimaryKeySelective(bgmsBlog);

        if (count > 0) {
            System.out.println("BgmsBlogServiceImpl--"+bgmsBlog.getId());
            return count;
        } else {
            return 0;
        }
    }


    @Override
    public int blogdel(Long umsId , Long id) {

        BgmsBlog bgmsBlog = bgmsBlogMapper.selectByPrimaryKey(id);
        if(bgmsBlog.getUmsId() == umsId){
            bgmsBlog.setState(4);
            int count = bgmsBlogMapper.updateByPrimaryKeySelective(bgmsBlog);
            //从 es 中删除当前博文
            return count;

        }
        return 0;
    }

    @Override
    public List<BgmsBlogParam> viewbloglist(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<BgmsBlogParam> lists = blogDao.getAllBlogList();
        return lists;
    }


    @Override
    public List<BgmsBlogParam> viewbloglistByUmsId(Long userId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<BgmsBlogParam> lists = blogDao.selectByUmsId(userId);
        return lists;
    }



    @Override
    public List<BgmsBlog> bloglist(Long userId,Integer state,String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        BgmsBlogExample example = new BgmsBlogExample();
        BgmsBlogExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andTitleLike("%" + keyword + "%");
        }
        //如果没有userID说明是查询全部的博文列表
        if(userId != null){
            //获取当前登陆账号的所有博文
            criteria.andUmsIdEqualTo(userId);
            if(state == 100){
                //获取除已删除外所有的博文
                criteria.andStateNotEqualTo(4);
            }else{
                //获取单状态的的博文
                criteria.andStateEqualTo(state);
            }
        }else {
            //获取除已发布的所有的博文
            criteria.andStateEqualTo(2);
        }

        List<BgmsBlog> lists = bgmsBlogMapper.selectByExample(example);
        return lists;
    }


    @Override
    public BgmsBlogParam bloginfo(Long blogId) {
        BgmsBlogParam bgmsBlog = bgmsBlogTagClassifyDao.selectBlogInfoByBlogID(blogId);
        if(bgmsBlog != null){
            return bgmsBlog;
        }

        return null;
    }


}
