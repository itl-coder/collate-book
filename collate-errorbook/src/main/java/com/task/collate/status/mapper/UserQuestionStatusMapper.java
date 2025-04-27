package com.task.collate.status.mapper;

import java.util.List;
import com.task.collate.status.domain.UserQuestionStatus;

/**
 * 用户题目状态Mapper接口
 * 
 * @author collate
 * @date 2025-04-23
 */
public interface UserQuestionStatusMapper 
{
    /**
     * 查询用户题目状态
     * 
     * @param userId 用户题目状态主键
     * @return 用户题目状态
     */
    public UserQuestionStatus selectUserQuestionStatusByUserId(Long userId);

    /**
     * 查询用户题目状态列表
     * 
     * @param userQuestionStatus 用户题目状态
     * @return 用户题目状态集合
     */
    public List<UserQuestionStatus> selectUserQuestionStatusList(UserQuestionStatus userQuestionStatus);

    /**
     * 新增用户题目状态
     * 
     * @param userQuestionStatus 用户题目状态
     * @return 结果
     */
    public int insertUserQuestionStatus(UserQuestionStatus userQuestionStatus);

    /**
     * 修改用户题目状态
     * 
     * @param userQuestionStatus 用户题目状态
     * @return 结果
     */
    public int updateUserQuestionStatus(UserQuestionStatus userQuestionStatus);

    /**
     * 删除用户题目状态
     * 
     * @param userId 用户题目状态主键
     * @return 结果
     */
    public int deleteUserQuestionStatusByUserId(Long userId);

    /**
     * 批量删除用户题目状态
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserQuestionStatusByUserIds(Long[] userIds);
}
