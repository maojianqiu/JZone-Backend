package com.blog.mbg.mapper;

import com.blog.mbg.model.BgmsBlogClassifyRelation;
import com.blog.mbg.model.BgmsBlogClassifyRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BgmsBlogClassifyRelationMapper {
    long countByExample(BgmsBlogClassifyRelationExample example);

    int deleteByExample(BgmsBlogClassifyRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BgmsBlogClassifyRelation record);

    int insertSelective(BgmsBlogClassifyRelation record);

    List<BgmsBlogClassifyRelation> selectByExample(BgmsBlogClassifyRelationExample example);

    BgmsBlogClassifyRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BgmsBlogClassifyRelation record, @Param("example") BgmsBlogClassifyRelationExample example);

    int updateByExample(@Param("record") BgmsBlogClassifyRelation record, @Param("example") BgmsBlogClassifyRelationExample example);

    int updateByPrimaryKeySelective(BgmsBlogClassifyRelation record);

    int updateByPrimaryKey(BgmsBlogClassifyRelation record);
}