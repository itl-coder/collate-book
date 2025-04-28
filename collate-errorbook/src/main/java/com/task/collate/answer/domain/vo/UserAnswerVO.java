package com.task.collate.answer.domain.vo;

import com.task.collate.answer.domain.UserAnswer;
import lombok.Data;

@Data
public class UserAnswerVO extends UserAnswer {
    private String subjectName;
    private String bookName;
    private String tagName;
    private String correctAnswer; // 给出的正确答案
    private String questionContent; // 题目内容
}
