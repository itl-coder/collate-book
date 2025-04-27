package com.task.collate.carousel.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.task.collate.common.annotation.Excel;
import com.task.collate.common.core.domain.BaseEntity;

/**
 * 轮播图对象 carousel
 * 
 * @author collate
 * @date 2025-04-24
 */
public class Carousel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 轮播图ID */
    private Long id;

    /** 轮播图标题 */
    @Excel(name = "轮播图标题")
    private String title;

    /** 图片URL */
    @Excel(name = "图片URL")
    private String imageUrl;

    /** 点击跳转链接 */
    @Excel(name = "点击跳转链接")
    private String linkUrl;

    /** 描述文字 */
    @Excel(name = "描述文字")
    private String description;

    /** 排序权重(数字越大越靠前) */
    @Excel(name = "排序权重(数字越大越靠前)")
    private Long sortOrder;

    /** 展示开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "展示开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 展示结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "展示结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 是否启用(0-禁用,1-启用) */
    @Excel(name = "是否启用(0-禁用,1-启用)")
    private Integer isActive;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }
    public void setLinkUrl(String linkUrl) 
    {
        this.linkUrl = linkUrl;
    }

    public String getLinkUrl() 
    {
        return linkUrl;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setSortOrder(Long sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder() 
    {
        return sortOrder;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setIsActive(Integer isActive) 
    {
        this.isActive = isActive;
    }

    public Integer getIsActive() 
    {
        return isActive;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }
    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("imageUrl", getImageUrl())
            .append("linkUrl", getLinkUrl())
            .append("description", getDescription())
            .append("sortOrder", getSortOrder())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("isActive", getIsActive())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
