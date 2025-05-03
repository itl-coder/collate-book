package com.task.collate.answer.service.impl;

import com.task.collate.answer.domain.UserAnswer;
import com.task.collate.answer.domain.vo.UserAnswerVO;
import com.task.collate.answer.mapper.UserAnswerMapper;
import com.task.collate.answer.service.IUserAnswerService;
import com.task.collate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户作答Service业务层处理
 *
 * @author collate
 * @date 2025-04-28
 */
@Service
public class UserAnswerServiceImpl implements IUserAnswerService {
    @Autowired
    private UserAnswerMapper userAnswerMapper;

    /**
     * 查询用户作答
     *
     * @param id 用户作答主键
     * @return 用户作答
     */
    @Override
    public UserAnswer selectUserAnswerById(Long id) {
        return userAnswerMapper.selectUserAnswerById(id);
    }

    /**
     * 查询用户作答列表
     *
     * @param userAnswer 用户作答
     * @return 用户作答
     */
    @Override
    public List<UserAnswer> selectUserAnswerList(UserAnswer userAnswer) {
        return userAnswerMapper.selectUserAnswerList(userAnswer);
    }

    /**
     * 新增用户作答
     *
     * @param userAnswer 用户作答
     * @return 结果
     */
    @Override
    public int insertUserAnswer(UserAnswer userAnswer) {
        // 查询题目是否已经加入,如果存在未加入错题本,修改状态
        Integer existFlag = userAnswerMapper.countQuestionExist(userAnswer.getQuestionId());
        if (existFlag > 0) {
            // 修改为加入错题本
           throw new RuntimeException("题目已经加入我的错题本!");
        }
        // 查询题目是否已经加入,如果存在已加入错题本,提示该题目已存在
        userAnswer.setCreateTime(DateUtils.getNowDate());
        return userAnswerMapper.insertUserAnswer(userAnswer);
    }

    /**
     * 修改用户作答
     *
     * @param userAnswer 用户作答
     * @return 结果
     */
    @Override
    public int updateUserAnswer(UserAnswer userAnswer) {
        userAnswer.setUpdateTime(DateUtils.getNowDate());
        return userAnswerMapper.updateUserAnswer(userAnswer);
    }

    /**
     * 批量删除用户作答
     *
     * @param ids 需要删除的用户作答主键
     * @return 结果
     */
    @Override
    public int deleteUserAnswerByIds(Long[] ids) {
        return userAnswerMapper.deleteUserAnswerByIds(ids);
    }

    /**
     * 删除用户作答信息
     *
     * @param id 用户作答主键
     * @return 结果
     */
    @Override
    public int deleteUserAnswerById(Long id) {
        return userAnswerMapper.deleteUserAnswerById(id);
    }

    @Override
    public List<UserAnswerVO> selectFrontUserAnswerList(Integer userId,
                                                        String subjectName,
                                                        String bookName
    ) {
        return userAnswerMapper.selectFrontUserAnswerList(userId, subjectName,bookName);
    }
}
