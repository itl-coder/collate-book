package com.task.collate.errorquestion.service.impl;

import java.util.List;
import com.task.collate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.collate.errorquestion.mapper.ErrorquestionMapper;
import com.task.collate.errorquestion.domain.Errorquestion;
import com.task.collate.errorquestion.service.IErrorquestionService;

/**
 * 错题Service业务层处理
 * 
 * @author collate
 * @date 2025-04-23
 */
@Service
public class ErrorquestionServiceImpl implements IErrorquestionService 
{
    @Autowired
    private ErrorquestionMapper errorquestionMapper;

    /**
     * 查询错题
     * 
     * @param id 错题主键
     * @return 错题
     */
    @Override
    public Errorquestion selectErrorquestionById(Long id)
    {
        return errorquestionMapper.selectErrorquestionById(id);
    }

    /**
     * 查询错题列表
     * 
     * @param errorquestion 错题
     * @return 错题
     */
    @Override
    public List<Errorquestion> selectErrorquestionList(Errorquestion errorquestion)
    {
        return errorquestionMapper.selectErrorquestionList(errorquestion);
    }

    /**
     * 新增错题
     * 
     * @param errorquestion 错题
     * @return 结果
     */
    @Override
    public int insertErrorquestion(Errorquestion errorquestion)
    {
        errorquestion.setCreateTime(DateUtils.getNowDate());
        return errorquestionMapper.insertErrorquestion(errorquestion);
    }

    /**
     * 修改错题
     * 
     * @param errorquestion 错题
     * @return 结果
     */
    @Override
    public int updateErrorquestion(Errorquestion errorquestion)
    {
        errorquestion.setUpdateTime(DateUtils.getNowDate());
        return errorquestionMapper.updateErrorquestion(errorquestion);
    }

    /**
     * 批量删除错题
     * 
     * @param ids 需要删除的错题主键
     * @return 结果
     */
    @Override
    public int deleteErrorquestionByIds(Long[] ids)
    {
        return errorquestionMapper.deleteErrorquestionByIds(ids);
    }

    /**
     * 删除错题信息
     * 
     * @param id 错题主键
     * @return 结果
     */
    @Override
    public int deleteErrorquestionById(Long id)
    {
        return errorquestionMapper.deleteErrorquestionById(id);
    }
}
