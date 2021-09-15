package com.blog.mbg.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class BgmsBlog implements Serializable {
    private Long id;

    @ApiModelProperty(value = "是否需要赞赏：0-否，1-是")
    private Boolean appreciation;

    @ApiModelProperty(value = "是否推荐：默认1，0-否，1-是")
    private Boolean commentabled;

    private String title;

    @ApiModelProperty(value = "摘要")
    private String description;

    @ApiModelProperty(value = "摘要图片")
    private String firstPicture;

    private Date createTime;

    @ApiModelProperty(value = "最后一次更新日期")
    private Date updateTime;

    @ApiModelProperty(value = "原创还是转载")
    private Boolean flag;

    @ApiModelProperty(value = "转载地址")
    private String flagUrl;

    @ApiModelProperty(value = "是否可以评论：0-否，1-是")
    private Boolean recommend;

    @ApiModelProperty(value = "是否可以分享")
    private Boolean shareStatement;

    @ApiModelProperty(value = "状态：0-草稿，1-审核中，2-已发布，3-未通过")
    private Integer state;

    private Long umsId;

    private String content;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Boolean appreciation) {
        this.appreciation = appreciation;
    }

    public Boolean getCommentabled() {
        return commentabled;
    }

    public void setCommentabled(Boolean commentabled) {
        this.commentabled = commentabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Boolean getShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(Boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getUmsId() {
        return umsId;
    }

    public void setUmsId(Long umsId) {
        this.umsId = umsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", appreciation=").append(appreciation);
        sb.append(", commentabled=").append(commentabled);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", firstPicture=").append(firstPicture);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", flag=").append(flag);
        sb.append(", flagUrl=").append(flagUrl);
        sb.append(", recommend=").append(recommend);
        sb.append(", shareStatement=").append(shareStatement);
        sb.append(", state=").append(state);
        sb.append(", umsId=").append(umsId);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}