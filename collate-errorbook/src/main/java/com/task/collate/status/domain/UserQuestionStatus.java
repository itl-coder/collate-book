package com.task.collate.status.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.task.collate.common.annotation.Excel;
import com.task.collate.common.core.domain.BaseEntity;

/**
 * 用户题目状态对象 user_question_status
 * 
 * @author collate
 * @date 2025-04-23
 */
public class UserQuestionStatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 题目ID */
    private Long questionId;

    /** 是否在错题本中，0表示否，1表示是 */
    @Excel(name = "是否在错题本中，0表示否，1表示是")
    private Long isInErrorbook;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setQuestionId(Long questionId) 
    {
        this.questionId = questionId;
    }

    public Long getQuestionId() 
    {
        return questionId;
    }
    public void setIsInErrorbook(Long isInErrorbook) 
    {
        this.isInErrorbook = isInErrorbook;
    }

    public Long getIsInErrorbook() 
    {
        return isInErrorbook;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("questionId", getQuestionId())
            .append("isInErrorbook", getIsInErrorbook())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
