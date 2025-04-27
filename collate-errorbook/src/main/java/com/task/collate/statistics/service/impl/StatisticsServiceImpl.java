package com.task.collate.statistics.service.impl;

import java.util.List;
import com.task.collate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.collate.statistics.mapper.StatisticsMapper;
import com.task.collate.statistics.domain.Statistics;
import com.task.collate.statistics.service.IStatisticsService;

/**
 * 错题统计Service业务层处理
 * 
 * @author collate
 * @date 2025-04-23
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService 
{
    @Autowired
    private StatisticsMapper statisticsMapper;

    /**
     * 查询错题统计
     * 
     * @param id 错题统计主键
     * @return 错题统计
     */
    @Override
    public Statistics selectStatisticsById(Long id)
    {
        return statisticsMapper.selectStatisticsById(id);
    }

    /**
     * 查询错题统计列表
     * 
     * @param statistics 错题统计
     * @return 错题统计
     */
    @Override
    public List<Statistics> selectStatisticsList(Statistics statistics)
    {
        return statisticsMapper.selectStatisticsList(statistics);
    }

    /**
     * 新增错题统计
     * 
     * @param statistics 错题统计
     * @return 结果
     */
    @Override
    public int insertStatistics(Statistics statistics)
    {
        statistics.setCreateTime(DateUtils.getNowDate());
        return statisticsMapper.insertStatistics(statistics);
    }

    /**
     * 修改错题统计
     * 
     * @param statistics 错题统计
     * @return 结果
     */
    @Override
    public int updateStatistics(Statistics statistics)
    {
        statistics.setUpdateTime(DateUtils.getNowDate());
        return statisticsMapper.updateStatistics(statistics);
    }

    /**
     * 批量删除错题统计
     * 
     * @param ids 需要删除的错题统计主键
     * @return 结果
     */
    @Override
    public int deleteStatisticsByIds(Long[] ids)
    {
        return statisticsMapper.deleteStatisticsByIds(ids);
    }

    /**
     * 删除错题统计信息
     * 
     * @param id 错题统计主键
     * @return 结果
     */
    @Override
    public int deleteStatisticsById(Long id)
    {
        return statisticsMapper.deleteStatisticsById(id);
    }
}
