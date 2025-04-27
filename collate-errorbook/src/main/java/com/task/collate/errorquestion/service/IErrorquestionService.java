package com.task.collate.errorquestion.service;

import java.util.List;
import com.task.collate.errorquestion.domain.Errorquestion;

/**
 * 错题Service接口
 * 
 * @author collate
 * @date 2025-04-23
 */
public interface IErrorquestionService 
{
    /**
     * 查询错题
     * 
     * @param id 错题主键
     * @return 错题
     */
    public Errorquestion selectErrorquestionById(Long id);

    /**
     * 查询错题列表
     * 
     * @param errorquestion 错题
     * @return 错题集合
     */
    public List<Errorquestion> selectErrorquestionList(Errorquestion errorquestion);

    /**
     * 新增错题
     * 
     * @param errorquestion 错题
     * @return 结果
     */
    public int insertErrorquestion(Errorquestion errorquestion);

    /**
     * 修改错题
     * 
     * @param errorquestion 错题
     * @return 结果
     */
    public int updateErrorquestion(Errorquestion errorquestion);

    /**
     * 批量删除错题
     * 
     * @param ids 需要删除的错题主键集合
     * @return 结果
     */
    public int deleteErrorquestionByIds(Long[] ids);

    /**
     * 删除错题信息
     * 
     * @param id 错题主键
     * @return 结果
     */
    public int deleteErrorquestionById(Long id);
}
