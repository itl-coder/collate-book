package com.task.collate.option.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.task.collate.common.annotation.Excel;
import com.task.collate.common.core.domain.BaseEntity;

/**
 * 选项，用于存储每道题的选项及其是否为正确答案对象 options
 * 
 * @author collate
 * @date 2025-04-25
 */
public class Options extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 选项的唯一标识符，主键，用于唯一标识每个选项 */
    private Long id;

    /** 外键，关联题目表 (questions)，用于标识这个选项属于哪道题 */
    @Excel(name = "外键，关联题目表 (questions)，用于标识这个选项属于哪道题")
    private Long questionId;

    /** 选项标识符，如：A、B、C、D */
    @Excel(name = "选项标识符，如：A、B、C、D")
    private String optionLabel;

    /** 选项内容，表示题目中的具体选项内容 */
    @Excel(name = "选项内容，表示题目中的具体选项内容")
    private String content;

    /** 是否为正确答案，布尔值，表示该选项是否正确 */
    @Excel(name = "是否为正确答案，布尔值，表示该选项是否正确")
    private Integer isCorrect;

    /** 记录创建时间，自动生成 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录创建时间，自动生成", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 记录更新时间，每次更新时自动生成 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录更新时间，每次更新时自动生成", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setQuestionId(Long questionId) 
    {
        this.questionId = questionId;
    }

    public Long getQuestionId() 
    {
        return questionId;
    }
    public void setOptionLabel(String optionLabel) 
    {
        this.optionLabel = optionLabel;
    }

    public String getOptionLabel() 
    {
        return optionLabel;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setIsCorrect(Integer isCorrect) 
    {
        this.isCorrect = isCorrect;
    }

    public Integer getIsCorrect() 
    {
        return isCorrect;
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
            .append("questionId", getQuestionId())
            .append("optionLabel", getOptionLabel())
            .append("content", getContent())
            .append("isCorrect", getIsCorrect())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
