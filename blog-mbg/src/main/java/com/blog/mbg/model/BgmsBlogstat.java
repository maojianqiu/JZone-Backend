package com.blog.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class BgmsBlogstat implements Serializable {
    private Long id;

    @ApiModelProperty(value = "博文id")
    private Long blogId;

    @ApiModelProperty(value = "浏览量")
    private Integer views;

    @ApiModelProperty(value = "点赞量")
    private Integer likes;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", blogId=").append(blogId);
        sb.append(", views=").append(views);
        sb.append(", likes=").append(likes);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}