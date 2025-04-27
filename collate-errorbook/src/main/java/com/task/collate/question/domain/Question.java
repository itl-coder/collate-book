package com.task.collate.question.domain;

import com.task.collate.book.domain.Book;
import com.task.collate.common.annotation.Excel;
import com.task.collate.common.core.domain.BaseEntity;
import com.task.collate.subject.domain.Subject;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 题目管理对象 question
 *
 * @author collate
 * @date 2025-04-23
 */
public class Question extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 题目主键
     */
    private Long id;

    /**
     * 题目内容
     */
    @Excel(name = "题目内容")
    private String questionContent;

    /**
     * 题目类型
     */
    @Excel(name = "题目类型")
    private String questionType;

    /**
     * 正确答案
     */
    @Excel(name = "正确答案")
    private String correctAnswer;

    /**
     * 所属科目ID
     */
    @Excel(name = "所属科目ID")
    private Long subjectId;


    private Subject subject;
    private Book book;

    /**
     * 所属教辅ID，可为空
     */
    @Excel(name = "所属教辅ID，可为空")
    private Long bookId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("questionContent", getQuestionContent())
                .append("questionType", getQuestionType())
                .append("correctAnswer", getCorrectAnswer())
                .append("subjectId", getSubjectId())
                .append("bookId", getBookId())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
