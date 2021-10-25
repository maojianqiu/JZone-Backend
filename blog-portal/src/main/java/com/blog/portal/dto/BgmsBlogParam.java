package com.blog.portal.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * description: BgmsBlogParam <br>
 * date: 2021/8/29 18:39 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */

@Data
public class BgmsBlogParam {
    @ApiModelProperty(value = "博文ID")
    private Long id;

    @ApiModelProperty(value = "是否需要赞赏：0-否，1-是")
    private Boolean appreciation;

    @ApiModelProperty(value = "是否推荐：默认1，0-否，1-是")
    private Boolean commentabled;

    @ApiModelProperty(value = "博文标题")
    private String title;

    @ApiModelProperty(value = "摘要")
    private String description;

    @ApiModelProperty(value = "摘要图片")
    private String firstPicture;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "最后一次更新日期")
    private Date updateTime;

    @ApiModelProperty(value = "原创还是转载 0-转载，1-原创")
    private Boolean flag;

    @ApiModelProperty(value = "转载地址")
    private String flagUrl;

    @ApiModelProperty(value = "是否可以评论：0-否，1-是")
    private Boolean recommend;

    @ApiModelProperty(value = "是否可以分享")
    private Boolean shareStatement;

    @ApiModelProperty(value = "状态：0-草稿，1-审核中，2-已发布，3-未通过 ")
    private Integer state;

    @ApiModelProperty(value = "博文作者ID")
    private Long umsId;

    @ApiModelProperty(value = "博文作者昵称")
    private String nickname;

    @ApiModelProperty(value = "博文作者头像")
    private String icon;

    @ApiModelProperty(value = "博文内容")
    private String content;

    @ApiModelProperty(value = "浏览量")
    private Integer views;

    @ApiModelProperty(value = "点赞量")
    private Integer likes;

    @ApiModelProperty(value = "当前登录用户是否点赞")
    private Boolean islike;

    @ApiModelProperty(value = "博文分类id列表")
    private List<Long> classifies;

    @ApiModelProperty(value = "博文标签列表")
    private List<BgmsTagParam> tags;
}
