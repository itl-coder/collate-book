package com.task.collate.errorquestion.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.task.collate.common.annotation.Excel;
import com.task.collate.common.core.domain.BaseEntity;

/**
 * 错题对象 errorquestion
 * 
 * @author collate
 * @date 2025-04-23
 */
public class Errorquestion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 错题主键 */
    private Long id;

    /** 题目ID */
    @Excel(name = "题目ID")
    private Long questionId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户错误答案 */
    @Excel(name = "用户错误答案")
    private String wrongAnswer;

    /** 重要程度等级，可空 */
    @Excel(name = "重要程度等级，可空")
    private Long importanceLevel;

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
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setWrongAnswer(String wrongAnswer) 
    {
        this.wrongAnswer = wrongAnswer;
    }

    public String getWrongAnswer() 
    {
        return wrongAnswer;
    }
    public void setImportanceLevel(Long importanceLevel) 
    {
        this.importanceLevel = importanceLevel;
    }

    public Long getImportanceLevel() 
    {
        return importanceLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("questionId", getQuestionId())
            .append("userId", getUserId())
            .append("wrongAnswer", getWrongAnswer())
            .append("importanceLevel", getImportanceLevel())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
