package com.blog.mbg.mapper;

import com.blog.mbg.model.BgmsTag;
import com.blog.mbg.model.BgmsTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BgmsTagMapper {
    long countByExample(BgmsTagExample example);

    int deleteByExample(BgmsTagExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BgmsTag record);

    int insertSelective(BgmsTag record);

    List<BgmsTag> selectByExample(BgmsTagExample example);

    BgmsTag selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BgmsTag record, @Param("example") BgmsTagExample example);

    int updateByExample(@Param("record") BgmsTag record, @Param("example") BgmsTagExample example);

    int updateByPrimaryKeySelective(BgmsTag record);

    int updateByPrimaryKey(BgmsTag record);
}