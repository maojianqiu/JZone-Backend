package com.blog.portal.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * description: BgmsClassifyParam <br>
 * date: 2021/8/29 18:58 <br>
 * author: vae <br>
 * version: 1.0 <br>
 */
@Data
public class BgmsClassifyParam {

    @ApiModelProperty(value = "博文分类id")
    private Long id;

    @ApiModelProperty(value = "所属用户id")
    private Long umsId;

    @ApiModelProperty(value = "分类名称")
    private String name;
}
