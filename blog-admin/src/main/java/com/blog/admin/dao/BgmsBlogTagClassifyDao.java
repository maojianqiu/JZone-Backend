package com.blog.admin.dao;

import com.blog.mbg.model.BgmsBlogClassifyRelation;
import com.blog.mbg.model.BgmsClassify;
import com.blog.mbg.model.BgmsTag;
import com.blog.mbg.model.UmsAdminRoleRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BgmsBlogTagClassifyDao {

    /**
     * 批量插入用户角色关系
     */
    int insertClassifyList(@Param("list") List<BgmsBlogClassifyRelation> bgmsBlogClassifyRelations);
}
