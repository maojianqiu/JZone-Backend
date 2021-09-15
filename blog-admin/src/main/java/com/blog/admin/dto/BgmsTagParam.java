package com.blog.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * description: BgmsTagParam <br>
 * date: 2021/8/29 18:57 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */
@Data
public class BgmsTagParam {

    @ApiModelProperty(value = "标签ID")
    private Long id;

    @ApiModelProperty(value = "博文id")
    private Long blogId;

    @ApiModelProperty(value = "标签内容")
    private String name;
}
