package com.blog.search.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Document(indexName = "bms", shards = 1, replicas = 0)
public class EsBlog implements Serializable {

    @Id
    private Long id;

    @ApiModelProperty(value = "是否需要赞赏：0-否，1-是")
    private Boolean appreciation;

    @ApiModelProperty(value = "是否推荐：默认1，0-否，1-是")
    private Boolean commentabled;

    @ApiModelProperty(value = "博文标题")
    @Field(type = FieldType.Text,fielddata = true, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String title;

    @ApiModelProperty(value = "摘要")
    @Field(type = FieldType.Text,fielddata = true, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
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
    @Field(type = FieldType.Text,fielddata = true, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String nickname;

    @ApiModelProperty(value = "博文内容")
    @Field(type = FieldType.Text,fielddata = true, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String content;

//    @ApiModelProperty(value = "博文分类id列表")
//    private List<Long> classifies;

    @ApiModelProperty(value = "博文标签列表")
    private List<EsBlogTag> tags;
}
