package com.task.collate.web.controller.visual;

import com.task.collate.answer.mapper.UserAnswerMapper;
import com.task.collate.common.core.controller.BaseController;
import com.task.collate.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class ErroBookVisual extends BaseController {
    @Resource
    private UserAnswerMapper userAnswerMapper;

    // 获取用户的错题统计
    @GetMapping("/{userId}")
    public AjaxResult loginUserErrorCollate(@PathVariable Long userId){
     List<Map<String,Object>> lineMap = userAnswerMapper.lineList(userId);
        return success(lineMap);
    }
}
