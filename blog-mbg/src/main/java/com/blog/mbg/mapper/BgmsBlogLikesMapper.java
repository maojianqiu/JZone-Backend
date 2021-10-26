package com.blog.mbg.mapper;

import com.blog.mbg.model.BgmsBlogLikes;
import com.blog.mbg.model.BgmsBlogLikesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BgmsBlogLikesMapper {
    long countByExample(BgmsBlogLikesExample example);

    int deleteByExample(BgmsBlogLikesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BgmsBlogLikes record);

    int insertSelective(BgmsBlogLikes record);

    List<BgmsBlogLikes> selectByExample(BgmsBlogLikesExample example);

    BgmsBlogLikes selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BgmsBlogLikes record, @Param("example") BgmsBlogLikesExample example);

    int updateByExample(@Param("record") BgmsBlogLikes record, @Param("example") BgmsBlogLikesExample example);

    int updateByPrimaryKeySelective(BgmsBlogLikes record);

    int updateByPrimaryKey(BgmsBlogLikes record);
}