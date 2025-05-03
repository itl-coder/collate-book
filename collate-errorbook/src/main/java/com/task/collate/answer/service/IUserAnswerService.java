package com.task.collate.answer.service;

import com.task.collate.answer.domain.UserAnswer;
import com.task.collate.answer.domain.vo.UserAnswerVO;

import java.util.List;

/**
 * 用户作答Service接口
 *
 * @author collate
 * @date 2025-04-28
 */
public interface IUserAnswerService {
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
     * 批量删除用户作答
     *
     * @param ids 需要删除的用户作答主键集合
     * @return 结果
     */
    public int deleteUserAnswerByIds(Long[] ids);

    /**
     * 删除用户作答信息
     *
     * @param id 用户作答主键
     * @return 结果
     */
    public int deleteUserAnswerById(Long id);

    public List<UserAnswerVO> selectFrontUserAnswerList(Integer userId,
                                                        Integer subjectId,
                                                        Integer tagId
    );
}
