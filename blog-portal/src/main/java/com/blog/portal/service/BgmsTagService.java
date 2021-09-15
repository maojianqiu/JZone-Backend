package com.blog.portal.service;

import com.blog.mbg.model.BgmsTag;
import com.blog.portal.dto.BgmsTagParam;

import java.util.List;

/**
 * description: BgmsTagService <br>
 * date: 2021/8/30 20:53 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */
public interface BgmsTagService {

    /*
     * 新增标签
     * */
    public int tagAdd(Long blogId, List<BgmsTagParam> bgmsTagParams);

    /*
     * 修改标签
     * */
    public int tagUpdate(Long blogId, List<BgmsTagParam> bgmsTagParams);

    /*
     * 获取标签列表
     * */
    public List<BgmsTag> taglist();

    /*
     * 获取标签列表
     * */
    public List<BgmsTag> taglistByBlogId(Long tagId);
    /*
     * 批量删除标签;
     * */
    public int tagdel(Long blogId, List<Long> ids);
    
}
