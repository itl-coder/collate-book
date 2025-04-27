package com.task.collate.question.domain.vo;

import com.task.collate.option.domain.Options;
import lombok.Data;

import java.util.List;

@Data
public class QuestionFrontVO extends QuestionVO {
    // 选项
    private List<Options> options;
}
