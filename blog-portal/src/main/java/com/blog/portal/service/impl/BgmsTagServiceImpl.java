package com.blog.portal.service.impl;

import com.blog.mbg.mapper.BgmsTagMapper;
import com.blog.mbg.model.BgmsTag;
import com.blog.mbg.model.BgmsTagExample;
import com.blog.portal.dto.BgmsTagParam;
import com.blog.portal.service.BgmsTagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description: BgmsTagServiceImpl <br>
 * date: 2021/8/30 20:55 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */

@Service
public class BgmsTagServiceImpl implements BgmsTagService {

    @Autowired
    BgmsTagMapper bgmsTagMapper;
//    @Autowired
//    BgmsBlogTagClassifyDao bgmsBlogTagClassifyDao;

    @Override
    public int tagAdd(Long blogId , List<BgmsTagParam> bgmsTagParams) {

        for (BgmsTagParam bgmsTagParam: bgmsTagParams) {
            BgmsTag bgmsTag = new BgmsTag();
            BeanUtils.copyProperties(bgmsTagParam,bgmsTag);
            bgmsTag.setBlogId(blogId);
            bgmsTagMapper.insert(bgmsTag);
        }
        return 0;
    }

    @Override
    public int tagUpdate(Long blogId , List<BgmsTagParam> bgmsTagParams) {
        //先删除原有关系
        BgmsTagExample bgmsTagExample = new BgmsTagExample();
        bgmsTagExample.createCriteria().andBlogIdEqualTo(blogId);
        bgmsTagMapper.deleteByExample(bgmsTagExample);

        //批量插入新关系
        for (BgmsTagParam bgmsTagParam: bgmsTagParams) {
            BgmsTag bgmsTag = new BgmsTag();
            BeanUtils.copyProperties(bgmsTagParam,bgmsTag);
            bgmsTag.setBlogId(blogId);
            bgmsTagMapper.insert(bgmsTag);
        }

        return 0;
    }

    @Override
    public List<BgmsTag> taglist() {

        return bgmsTagMapper.selectByExample(new BgmsTagExample());
    }

    @Override
    public List<BgmsTag> taglistByBlogId(Long blogId) {
        BgmsTagExample bgmsTagExample = new BgmsTagExample();
        bgmsTagExample.createCriteria().andBlogIdEqualTo(blogId);
        return bgmsTagMapper.selectByExample(bgmsTagExample);
    }


    @Override
    public int tagdel(Long blogId , List<Long> ids) {
        BgmsTagExample bgmsTagExample = new BgmsTagExample();
        bgmsTagExample.createCriteria().andBlogIdEqualTo(blogId);
        bgmsTagMapper.deleteByExample(bgmsTagExample);
        return 0;
    }
}
