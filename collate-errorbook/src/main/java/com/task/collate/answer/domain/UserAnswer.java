package com.task.collate.answer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.task.collate.common.annotation.Excel;
import com.task.collate.common.core.domain.BaseEntity;

/**
 * 用户作答对象 user_answer
 * 
 * @author collate
 * @date 2025-04-28
 */
public class UserAnswer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键，唯一标识一条作答记录 */
    private Long id;

    /** 用户ID，关联用户表 */
    @Excel(name = "用户ID，关联用户表")
    private Long userId;

    /** 题目ID，关联题目表 */
    @Excel(name = "题目ID，关联题目表")
    private Long questionId;

    /** 关联科目表 */
    @Excel(name = "关联科目表")
    private Long subjectId;

    /** 用户的作答内容，单选/多选或填空的答案 */
    @Excel(name = "用户的作答内容，单选/多选或填空的答案")
    private String answer;

    /** 正确答案 */
    @Excel(name = "正确答案")
    private String isCorrect;

    /** 用户作答此题的得分 */
    @Excel(name = "用户作答此题的得分")
    private Long score;

    /** 标签名 */
    @Excel(name = "标签名")
    private Long tagId;

    /** 排序字段，用户作答的顺序 */
    @Excel(name = "排序字段，用户作答的顺序")
    private Long sortOrder;

    /** 加入错题本,0:否,1:是 */
    @Excel(name = "加入错题本,0:否,1:是")
    private Long addCollate;

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
    public void setQuestionId(Long questionId) 
    {
        this.questionId = questionId;
    }

    public Long getQuestionId() 
    {
        return questionId;
    }
    public void setSubjectId(Long subjectId) 
    {
        this.subjectId = subjectId;
    }

    public Long getSubjectId() 
    {
        return subjectId;
    }
    public void setAnswer(String answer) 
    {
        this.answer = answer;
    }

    public String getAnswer() 
    {
        return answer;
    }
    public void setIsCorrect(String isCorrect) 
    {
        this.isCorrect = isCorrect;
    }

    public String getIsCorrect() 
    {
        return isCorrect;
    }
    public void setScore(Long score) 
    {
        this.score = score;
    }

    public Long getScore() 
    {
        return score;
    }
    public void setTagId(Long tagId) 
    {
        this.tagId = tagId;
    }

    public Long getTagId() 
    {
        return tagId;
    }
    public void setSortOrder(Long sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Long getSortOrder() 
    {
        return sortOrder;
    }
    public void setAddCollate(Long addCollate) 
    {
        this.addCollate = addCollate;
    }

    public Long getAddCollate() 
    {
        return addCollate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("questionId", getQuestionId())
            .append("subjectId", getSubjectId())
            .append("answer", getAnswer())
            .append("isCorrect", getIsCorrect())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("score", getScore())
            .append("tagId", getTagId())
            .append("sortOrder", getSortOrder())
            .append("addCollate", getAddCollate())
            .toString();
    }
}
