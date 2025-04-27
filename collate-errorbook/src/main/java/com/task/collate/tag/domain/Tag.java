package com.task.collate.tag.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.task.collate.common.annotation.Excel;
import com.task.collate.common.core.domain.BaseEntity;

/**
 * 错题标签对象 tag
 * 
 * @author collate
 * @date 2025-04-23
 */
public class Tag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标签主键 */
    private Long id;

    /** 标签名称 */
    @Excel(name = "标签名称")
    private String tagName;

    /** 所属用户ID */
    @Excel(name = "所属用户ID")
    private Long userId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTagName(String tagName) 
    {
        this.tagName = tagName;
    }

    public String getTagName() 
    {
        return tagName;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("tagName", getTagName())
            .append("userId", getUserId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
