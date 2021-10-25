package com.blog.mbg.mapper;

import com.blog.mbg.model.BgmsBlogstat;
import com.blog.mbg.model.BgmsBlogstatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BgmsBlogstatMapper {
    long countByExample(BgmsBlogstatExample example);

    int deleteByExample(BgmsBlogstatExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BgmsBlogstat record);

    int insertSelective(BgmsBlogstat record);

    List<BgmsBlogstat> selectByExample(BgmsBlogstatExample example);

    BgmsBlogstat selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BgmsBlogstat record, @Param("example") BgmsBlogstatExample example);

    int updateByExample(@Param("record") BgmsBlogstat record, @Param("example") BgmsBlogstatExample example);

    int updateByPrimaryKeySelective(BgmsBlogstat record);

    int updateByPrimaryKey(BgmsBlogstat record);
}