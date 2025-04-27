package com.task.collate.statistics.service;

import java.util.List;
import com.task.collate.statistics.domain.Statistics;

/**
 * 错题统计Service接口
 * 
 * @author collate
 * @date 2025-04-23
 */
public interface IStatisticsService 
{
    /**
     * 查询错题统计
     * 
     * @param id 错题统计主键
     * @return 错题统计
     */
    public Statistics selectStatisticsById(Long id);

    /**
     * 查询错题统计列表
     * 
     * @param statistics 错题统计
     * @return 错题统计集合
     */
    public List<Statistics> selectStatisticsList(Statistics statistics);

    /**
     * 新增错题统计
     * 
     * @param statistics 错题统计
     * @return 结果
     */
    public int insertStatistics(Statistics statistics);

    /**
     * 修改错题统计
     * 
     * @param statistics 错题统计
     * @return 结果
     */
    public int updateStatistics(Statistics statistics);

    /**
     * 批量删除错题统计
     * 
     * @param ids 需要删除的错题统计主键集合
     * @return 结果
     */
    public int deleteStatisticsByIds(Long[] ids);

    /**
     * 删除错题统计信息
     * 
     * @param id 错题统计主键
     * @return 结果
     */
    public int deleteStatisticsById(Long id);
}
