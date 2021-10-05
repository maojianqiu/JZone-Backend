package com.blog.search.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class EsBlogTag implements Serializable {

    @ApiModelProperty(value = "标签ID")
    private Long id;

    @ApiModelProperty(value = "博文id")
    private Long blogId;

    @ApiModelProperty(value = "标签内容")
    @Field(type = FieldType.Keyword)
    private String name;
}
