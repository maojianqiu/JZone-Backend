package com.blog.portal.service.impl;

import com.blog.mbg.mapper.BgmsBlogMapper;
import com.blog.mbg.model.BgmsBlog;
import com.blog.mbg.model.BgmsBlogExample;
import com.blog.mbg.model.BgmsTagExample;
import com.blog.portal.dao.BgmsBlogTagClassifyDao;
import com.blog.portal.dto.BgmsBlogParam;
import com.blog.portal.service.BgmsBlogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Long blogAdd(BgmsBlogParam bgmsClassifyParam) {

        BgmsBlog bgmsBlog = new BgmsBlog();
        BeanUtils.copyProperties(bgmsClassifyParam,bgmsBlog);
        bgmsBlog.setCreateTime(new Date());
        bgmsBlog.setUpdateTime(new Date());
        int count = bgmsBlogMapper.insertSelective(bgmsBlog);

        if (count > 0) {
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
    public BgmsBlogParam bloginfo(Long blogId) {
        BgmsBlogParam bgmsBlog = bgmsBlogTagClassifyDao.selectBlogInfoByBlogID(blogId);
        if(bgmsBlog != null){
            return bgmsBlog;
        }

        return null;
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
    public int blogdel(Long umsId , Long id) {

        BgmsBlog bgmsBlog = bgmsBlogMapper.selectByPrimaryKey(id);
        if(bgmsBlog.getUmsId() == umsId){
            bgmsBlog.setState(4);
            int count = bgmsBlogMapper.updateByPrimaryKeySelective(bgmsBlog);
            return count;

        }
        return 0;
    }
}
