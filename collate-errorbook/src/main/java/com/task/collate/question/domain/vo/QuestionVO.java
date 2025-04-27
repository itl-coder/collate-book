package com.task.collate.question.domain.vo;

import com.task.collate.common.annotation.Excel;
import com.task.collate.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class QuestionVO extends BaseEntity {
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

    /**
     * 所属教辅ID，可为空
     */
    @Excel(name = "所属教辅ID，可为空")
    private Long bookId;

    /**
     * 科目名称
     */
    @Excel(name = "科目名称")
    private String subjectName;


    /**
     * 教辅名称
     */
    @Excel(name = "教辅名称")
    private String bookName;
}
