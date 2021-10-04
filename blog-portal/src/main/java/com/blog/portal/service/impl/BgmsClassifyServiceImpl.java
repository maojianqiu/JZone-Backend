package com.blog.portal.service.impl;

import com.blog.mbg.mapper.BgmsBlogClassifyRelationMapper;
import com.blog.mbg.mapper.BgmsClassifyMapper;
import com.blog.mbg.model.BgmsBlogClassifyRelation;
import com.blog.mbg.model.BgmsBlogClassifyRelationExample;
import com.blog.mbg.model.BgmsClassify;
import com.blog.mbg.model.BgmsClassifyExample;
import com.blog.portal.dao.BgmsBlogTagClassifyDao;
import com.blog.portal.dto.BgmsClassifyParam;
import com.blog.portal.service.BgmsClassifyService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * description: BgmsClassifyServiceImpl <br>
 * date: 2021/8/29 21:31 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */

@Service
public class BgmsClassifyServiceImpl implements BgmsClassifyService {

    @Autowired
    BgmsClassifyMapper bgmsClassifyMapper;
    @Autowired
    BgmsBlogClassifyRelationMapper bgmsBlogClassifyRelationMapper;
    @Autowired
    BgmsBlogTagClassifyDao bgmsBlogTagClassifyDao;


    @Override
    public int classifyAdd(BgmsClassifyParam bgmsClassifyParam) {
        BgmsClassify bgmsClassify = new BgmsClassify();
        BeanUtils.copyProperties(bgmsClassifyParam,bgmsClassify);

        int count = bgmsClassifyMapper.insertSelective(bgmsClassify);


        return count;
    }

    @Override
    public int classifyUpdate(BgmsClassifyParam bgmsClassifyParam) {
        BgmsClassify bgmsClassify = new BgmsClassify();
        BeanUtils.copyProperties(bgmsClassifyParam,bgmsClassify);

        int count = bgmsClassifyMapper.updateByPrimaryKey(bgmsClassify);

        return count;
    }

    @Override
    public List<BgmsClassify> classifylist(Long umsid,String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        BgmsClassifyExample bgmsClassifyExample = new BgmsClassifyExample();
        BgmsClassifyExample.Criteria criteria = bgmsClassifyExample.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }

        criteria.andUmsIdEqualTo(umsid);
        return bgmsClassifyMapper.selectByExample(bgmsClassifyExample);
    }

    @Override
    public List<Long> classifylistByBlogId(Long blogId) {
        BgmsBlogClassifyRelationExample bgmsBlogClassifyRelationExample = new BgmsBlogClassifyRelationExample();
        bgmsBlogClassifyRelationExample.createCriteria().andBlogIdEqualTo(blogId);
        List<BgmsBlogClassifyRelation> bgmsBlogClassifyRelations = bgmsBlogClassifyRelationMapper.selectByExample(bgmsBlogClassifyRelationExample);
        List<Long> longList = new ArrayList<>();
        for (BgmsBlogClassifyRelation b : bgmsBlogClassifyRelations) {
            longList.add(b.getClassifyId());
        }

        return longList;
    }

    @Override
    public int classifydel(List<Long> classifyids) {
        int count = classifyids == null ? 0 : classifyids.size();

        //先删除 relation 中的classify
        BgmsBlogClassifyRelationExample bgmsBlogClassifyRelationExample = new BgmsBlogClassifyRelationExample();
        bgmsBlogClassifyRelationExample.createCriteria().andClassifyIdIn(classifyids);
        bgmsBlogClassifyRelationMapper.deleteByExample(bgmsBlogClassifyRelationExample);

        //再删除
        BgmsClassifyExample bgmsClassifyExample = new BgmsClassifyExample();
        bgmsClassifyExample.createCriteria().andIdIn(classifyids);
        bgmsClassifyMapper.deleteByExample(bgmsClassifyExample);



        return count;
    }

    @Override
    public int classifyBlogRelationUpdate(Integer state , Long blogId, List<Long> classifyids) {
        int count = classifyids == null ? 0 : classifyids.size();

        //如果是更新，则先删除原有的分类
        if (state == 1){
            //先删除分类，
            BgmsBlogClassifyRelationExample bgmsBlogClassifyRelationExample = new BgmsBlogClassifyRelationExample();
            bgmsBlogClassifyRelationExample.createCriteria().andBlogIdEqualTo(blogId);
            bgmsBlogClassifyRelationMapper.deleteByExample(bgmsBlogClassifyRelationExample);
        }

        //再新增博文所属分类；
        if (!CollectionUtils.isEmpty(classifyids)) {
            List<BgmsBlogClassifyRelation> list = new ArrayList<>();
            for (Long classifyId : classifyids) {
                BgmsBlogClassifyRelation relation = new BgmsBlogClassifyRelation();
                relation.setBlogId(blogId);
                relation.setClassifyId(classifyId);
                list.add(relation);
            }
            bgmsBlogTagClassifyDao.insertClassifyList(list);

        }

        // 0 就是 ids == null
        return count;
    }
}
