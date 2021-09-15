package com.blog.mbg.mapper;

import com.blog.mbg.model.BgmsClassify;
import com.blog.mbg.model.BgmsClassifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BgmsClassifyMapper {
    long countByExample(BgmsClassifyExample example);

    int deleteByExample(BgmsClassifyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BgmsClassify record);

    int insertSelective(BgmsClassify record);

    List<BgmsClassify> selectByExample(BgmsClassifyExample example);

    BgmsClassify selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BgmsClassify record, @Param("example") BgmsClassifyExample example);

    int updateByExample(@Param("record") BgmsClassify record, @Param("example") BgmsClassifyExample example);

    int updateByPrimaryKeySelective(BgmsClassify record);

    int updateByPrimaryKey(BgmsClassify record);
}