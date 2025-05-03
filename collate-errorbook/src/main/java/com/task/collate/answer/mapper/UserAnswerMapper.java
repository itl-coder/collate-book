package com.task.collate.answer.mapper;

import java.util.List;
import java.util.Map;

import com.task.collate.answer.domain.UserAnswer;
import com.task.collate.answer.domain.vo.UserAnswerVO;
import org.apache.ibatis.annotations.Param;

/**
 * 用户作答Mapper接口
 * 
 * @author collate
 * @date 2025-04-28
 */
public interface UserAnswerMapper 
{
    /**
     * 查询用户作答
     * 
     * @param id 用户作答主键
     * @return 用户作答
     */
    public UserAnswer selectUserAnswerById(Long id);

    /**
     * 查询用户作答列表
     * 
     * @param userAnswer 用户作答
     * @return 用户作答集合
     */
    public List<UserAnswer> selectUserAnswerList(UserAnswer userAnswer);

    /**
     * 新增用户作答
     * 
     * @param userAnswer 用户作答
     * @return 结果
     */
    public int insertUserAnswer(UserAnswer userAnswer);

    /**
     * 修改用户作答
     * 
     * @param userAnswer 用户作答
     * @return 结果
     */
    public int updateUserAnswer(UserAnswer userAnswer);

    /**
     * 删除用户作答
     * 
     * @param id 用户作答主键
     * @return 结果
     */
    public int deleteUserAnswerById(Long id);

    /**
     * 批量删除用户作答
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserAnswerByIds(Long[] ids);

   public List<UserAnswerVO> selectFrontUserAnswerList(@Param("userId") Integer userId,
                                                       @Param("subjectId") Integer subjectId,
                                                       @Param("tagId") Integer tagId
   );

   public Integer countQuestionExist(Long questionId);

   public List<Map<String,Object>> lineList(Long userId);
}
