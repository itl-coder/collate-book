package com.task.collate.question.service;

import java.util.List;
import com.task.collate.question.domain.Question;
import com.task.collate.question.domain.vo.QuestionFrontVO;
import com.task.collate.question.domain.vo.QuestionVO;

/**
 * 题目管理Service接口
 * 
 * @author collate
 * @date 2025-04-23
 */
public interface IQuestionService 
{
    /**
     * 查询题目管理
     * 
     * @param id 题目管理主键
     * @return 题目管理
     */
    public Question selectQuestionById(Long id);

    /**
     * 查询题目管理列表
     * 
     * @param question 题目管理
     * @return 题目管理集合
     */
    public List<QuestionVO> selectQuestionList(Question question);

    /**
     * 新增题目管理
     * 
     * @param question 题目管理
     * @return 结果
     */
    public int insertQuestion(Question question);

    /**
     * 修改题目管理
     * 
     * @param question 题目管理
     * @return 结果
     */
    public int updateQuestion(Question question);

    /**
     * 批量删除题目管理
     * 
     * @param ids 需要删除的题目管理主键集合
     * @return 结果
     */
    public int deleteQuestionByIds(Long[] ids);

    /**
     * 删除题目管理信息
     * 
     * @param id 题目管理主键
     * @return 结果
     */
    public int deleteQuestionById(Long id);

   public List<QuestionFrontVO> selectFrontQuestionList(Integer pageSize,Integer currentPage,String subjectName,String bookName);

    public   List<Question> selectCountQuestion();
}
