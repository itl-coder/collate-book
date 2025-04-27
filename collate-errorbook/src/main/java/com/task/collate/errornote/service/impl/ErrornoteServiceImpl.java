package com.task.collate.errornote.service.impl;

import java.util.List;
import com.task.collate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.collate.errornote.mapper.ErrornoteMapper;
import com.task.collate.errornote.domain.Errornote;
import com.task.collate.errornote.service.IErrornoteService;

/**
 * 错题笔记Service业务层处理
 * 
 * @author collate
 * @date 2025-04-23
 */
@Service
public class ErrornoteServiceImpl implements IErrornoteService 
{
    @Autowired
    private ErrornoteMapper errornoteMapper;

    /**
     * 查询错题笔记
     * 
     * @param id 错题笔记主键
     * @return 错题笔记
     */
    @Override
    public Errornote selectErrornoteById(Long id)
    {
        return errornoteMapper.selectErrornoteById(id);
    }

    /**
     * 查询错题笔记列表
     * 
     * @param errornote 错题笔记
     * @return 错题笔记
     */
    @Override
    public List<Errornote> selectErrornoteList(Errornote errornote)
    {
        return errornoteMapper.selectErrornoteList(errornote);
    }

    /**
     * 新增错题笔记
     * 
     * @param errornote 错题笔记
     * @return 结果
     */
    @Override
    public int insertErrornote(Errornote errornote)
    {
        errornote.setCreateTime(DateUtils.getNowDate());
        return errornoteMapper.insertErrornote(errornote);
    }

    /**
     * 修改错题笔记
     * 
     * @param errornote 错题笔记
     * @return 结果
     */
    @Override
    public int updateErrornote(Errornote errornote)
    {
        errornote.setUpdateTime(DateUtils.getNowDate());
        return errornoteMapper.updateErrornote(errornote);
    }

    /**
     * 批量删除错题笔记
     * 
     * @param ids 需要删除的错题笔记主键
     * @return 结果
     */
    @Override
    public int deleteErrornoteByIds(Long[] ids)
    {
        return errornoteMapper.deleteErrornoteByIds(ids);
    }

    /**
     * 删除错题笔记信息
     * 
     * @param id 错题笔记主键
     * @return 结果
     */
    @Override
    public int deleteErrornoteById(Long id)
    {
        return errornoteMapper.deleteErrornoteById(id);
    }
}
