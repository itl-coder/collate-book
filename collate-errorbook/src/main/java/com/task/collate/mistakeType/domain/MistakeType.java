package com.task.collate.mistakeType.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.task.collate.common.annotation.Excel;
import com.task.collate.common.core.domain.BaseEntity;

/**
 * 错题类型对象 mistake_type
 * 
 * @author collate
 * @date 2025-04-23
 */
public class MistakeType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 错误类型主键 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 错误名称 */
    @Excel(name = "错误名称")
    private String mistakeName;

    /** 错误描述 */
    @Excel(name = "错误描述")
    private String description;

    /** 出现次数 */
    @Excel(name = "出现次数")
    private Long count;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setMistakeName(String mistakeName) 
    {
        this.mistakeName = mistakeName;
    }

    public String getMistakeName() 
    {
        return mistakeName;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setCount(Long count) 
    {
        this.count = count;
    }

    public Long getCount() 
    {
        return count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("mistakeName", getMistakeName())
            .append("description", getDescription())
            .append("count", getCount())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
