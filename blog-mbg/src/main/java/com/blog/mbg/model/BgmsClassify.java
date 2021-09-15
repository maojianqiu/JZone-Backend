package com.blog.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class BgmsClassify implements Serializable {
    private Long id;

    @ApiModelProperty(value = "所属用户id")
    private Long umsId;

    @ApiModelProperty(value = "分类名称")
    private String name;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUmsId() {
        return umsId;
    }

    public void setUmsId(Long umsId) {
        this.umsId = umsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", umsId=").append(umsId);
        sb.append(", name=").append(name);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}