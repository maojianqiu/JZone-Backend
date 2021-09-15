package com.blog.mbg.mapper;

import com.blog.mbg.model.BgmsBlog;
import com.blog.mbg.model.BgmsBlogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BgmsBlogMapper {
    long countByExample(BgmsBlogExample example);

    int deleteByExample(BgmsBlogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BgmsBlog record);

    int insertSelective(BgmsBlog record);

    List<BgmsBlog> selectByExampleWithBLOBs(BgmsBlogExample example);

    List<BgmsBlog> selectByExample(BgmsBlogExample example);

    BgmsBlog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BgmsBlog record, @Param("example") BgmsBlogExample example);

    int updateByExampleWithBLOBs(@Param("record") BgmsBlog record, @Param("example") BgmsBlogExample example);

    int updateByExample(@Param("record") BgmsBlog record, @Param("example") BgmsBlogExample example);

    int updateByPrimaryKeySelective(BgmsBlog record);

    int updateByPrimaryKeyWithBLOBs(BgmsBlog record);

    int updateByPrimaryKey(BgmsBlog record);
}