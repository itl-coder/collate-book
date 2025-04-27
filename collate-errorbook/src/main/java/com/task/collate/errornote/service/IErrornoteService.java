package com.task.collate.errornote.service;

import java.util.List;
import com.task.collate.errornote.domain.Errornote;

/**
 * 错题笔记Service接口
 * 
 * @author collate
 * @date 2025-04-23
 */
public interface IErrornoteService 
{
    /**
     * 查询错题笔记
     * 
     * @param id 错题笔记主键
     * @return 错题笔记
     */
    public Errornote selectErrornoteById(Long id);

    /**
     * 查询错题笔记列表
     * 
     * @param errornote 错题笔记
     * @return 错题笔记集合
     */
    public List<Errornote> selectErrornoteList(Errornote errornote);

    /**
     * 新增错题笔记
     * 
     * @param errornote 错题笔记
     * @return 结果
     */
    public int insertErrornote(Errornote errornote);

    /**
     * 修改错题笔记
     * 
     * @param errornote 错题笔记
     * @return 结果
     */
    public int updateErrornote(Errornote errornote);

    /**
     * 批量删除错题笔记
     * 
     * @param ids 需要删除的错题笔记主键集合
     * @return 结果
     */
    public int deleteErrornoteByIds(Long[] ids);

    /**
     * 删除错题笔记信息
     * 
     * @param id 错题笔记主键
     * @return 结果
     */
    public int deleteErrornoteById(Long id);
}
