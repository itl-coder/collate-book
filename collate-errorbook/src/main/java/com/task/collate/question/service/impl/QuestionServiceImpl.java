package com.task.collate.question.service.impl;

import java.util.List;
import com.task.collate.common.utils.DateUtils;
import com.task.collate.question.domain.vo.QuestionFrontVO;
import com.task.collate.question.domain.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.collate.question.mapper.QuestionMapper;
import com.task.collate.question.domain.Question;
import com.task.collate.question.service.IQuestionService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 题目管理Service业务层处理
 * 
 * @author collate
 * @date 2025-04-23
 */
@Service
public class QuestionServiceImpl implements IQuestionService 
{
    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 查询题目管理
     * 
     * @param id 题目管理主键
     * @return 题目管理
     */
    @Override
    public Question selectQuestionById(Long id)
    {
        return questionMapper.selectQuestionById(id);
    }

    /**
     * 查询题目管理列表
     * 
     * @param question 题目管理
     * @return 题目管理
     */
    @Override
    public List<QuestionVO> selectQuestionList(Question question)
    {
        return questionMapper.selectQuestionList(question);
    }

    /**
     * 新增题目管理
     * 
     * @param question 题目管理
     * @return 结果
     */
    @Override
    public int insertQuestion(Question question)
    {
        question.setCreateTime(DateUtils.getNowDate());
        return questionMapper.insertQuestion(question);
    }

    /**
     * 修改题目管理
     * 
     * @param question 题目管理
     * @return 结果
     */
    @Override
    public int updateQuestion(Question question)
    {
        question.setUpdateTime(DateUtils.getNowDate());
        return questionMapper.updateQuestion(question);
    }

    /**
     * 批量删除题目管理
     * 
     * @param ids 需要删除的题目管理主键
     * @return 结果
     */
    @Override
    public int deleteQuestionByIds(Long[] ids)
    {
        return questionMapper.deleteQuestionByIds(ids);
    }

    /**
     * 删除题目管理信息
     * 
     * @param id 题目管理主键
     * @return 结果
     */
    @Override
    public int deleteQuestionById(Long id)
    {
        return questionMapper.deleteQuestionById(id);
    }

    @Override
    public List<QuestionFrontVO> selectFrontQuestionList(
            Integer pageSize,
           Integer currentPage,
           String subjectName,
           String bookName
    ) {

        return questionMapper.selectFrontQuestionList(pageSize,currentPage,subjectName,bookName);
    }

    @Override
    public List<Question> selectCountQuestion() {
        return questionMapper.selectCountFrontQuestionList();
    }
}
