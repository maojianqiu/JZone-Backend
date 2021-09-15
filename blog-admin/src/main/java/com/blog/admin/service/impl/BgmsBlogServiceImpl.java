package com.blog.admin.service.impl;

import com.blog.admin.dto.BgmsBlogParam;
import com.blog.admin.service.BgmsBlogService;
import com.blog.common.api.CommonResult;
import com.blog.mbg.mapper.BgmsBlogMapper;
import com.blog.mbg.model.BgmsBlog;
import com.blog.mbg.model.BgmsBlogExample;
import com.blog.mbg.model.BgmsClassify;
import com.blog.mbg.model.UmsAdminExample;
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
    public BgmsBlog bloginfo(Long blogId) {
        BgmsBlog bgmsBlog = bgmsBlogMapper.selectByPrimaryKey(blogId);
        if(bgmsBlog != null){
            return bgmsBlog;
        }

        return null;
    }

    @Override
    public List<BgmsBlog> bloglist(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        BgmsBlogExample example = new BgmsBlogExample();
        BgmsBlogExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andTitleLike("%" + keyword + "%");
        }
        List<BgmsBlog> lists = bgmsBlogMapper.selectByExample(example);
        return lists;
    }

    @Override
    public int blogdel(List<Long> ids) {
        return 0;
    }
}
