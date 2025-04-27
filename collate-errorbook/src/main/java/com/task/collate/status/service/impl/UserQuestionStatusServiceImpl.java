package com.task.collate.status.service.impl;

import java.util.List;
import com.task.collate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.collate.status.mapper.UserQuestionStatusMapper;
import com.task.collate.status.domain.UserQuestionStatus;
import com.task.collate.status.service.IUserQuestionStatusService;

/**
 * 用户题目状态Service业务层处理
 * 
 * @author collate
 * @date 2025-04-23
 */
@Service
public class UserQuestionStatusServiceImpl implements IUserQuestionStatusService 
{
    @Autowired
    private UserQuestionStatusMapper userQuestionStatusMapper;

    /**
     * 查询用户题目状态
     * 
     * @param userId 用户题目状态主键
     * @return 用户题目状态
     */
    @Override
    public UserQuestionStatus selectUserQuestionStatusByUserId(Long userId)
    {
        return userQuestionStatusMapper.selectUserQuestionStatusByUserId(userId);
    }

    /**
     * 查询用户题目状态列表
     * 
     * @param userQuestionStatus 用户题目状态
     * @return 用户题目状态
     */
    @Override
    public List<UserQuestionStatus> selectUserQuestionStatusList(UserQuestionStatus userQuestionStatus)
    {
        return userQuestionStatusMapper.selectUserQuestionStatusList(userQuestionStatus);
    }

    /**
     * 新增用户题目状态
     * 
     * @param userQuestionStatus 用户题目状态
     * @return 结果
     */
    @Override
    public int insertUserQuestionStatus(UserQuestionStatus userQuestionStatus)
    {
        userQuestionStatus.setCreateTime(DateUtils.getNowDate());
        return userQuestionStatusMapper.insertUserQuestionStatus(userQuestionStatus);
    }

    /**
     * 修改用户题目状态
     * 
     * @param userQuestionStatus 用户题目状态
     * @return 结果
     */
    @Override
    public int updateUserQuestionStatus(UserQuestionStatus userQuestionStatus)
    {
        userQuestionStatus.setUpdateTime(DateUtils.getNowDate());
        return userQuestionStatusMapper.updateUserQuestionStatus(userQuestionStatus);
    }

    /**
     * 批量删除用户题目状态
     * 
     * @param userIds 需要删除的用户题目状态主键
     * @return 结果
     */
    @Override
    public int deleteUserQuestionStatusByUserIds(Long[] userIds)
    {
        return userQuestionStatusMapper.deleteUserQuestionStatusByUserIds(userIds);
    }

    /**
     * 删除用户题目状态信息
     * 
     * @param userId 用户题目状态主键
     * @return 结果
     */
    @Override
    public int deleteUserQuestionStatusByUserId(Long userId)
    {
        return userQuestionStatusMapper.deleteUserQuestionStatusByUserId(userId);
    }
}
