package com.task.collate.subject.mapper;

import java.util.List;
import com.task.collate.subject.domain.Subject;

/**
 * 科目分类Mapper接口
 * 
 * @author collate
 * @date 2025-04-23
 */
public interface SubjectMapper 
{
    /**
     * 查询科目分类
     * 
     * @param id 科目分类主键
     * @return 科目分类
     */
    public Subject selectSubjectById(Long id);

    /**
     * 查询科目分类列表
     * 
     * @param subject 科目分类
     * @return 科目分类集合
     */
    public List<Subject> selectSubjectList(Subject subject);

    /**
     * 新增科目分类
     * 
     * @param subject 科目分类
     * @return 结果
     */
    public int insertSubject(Subject subject);

    /**
     * 修改科目分类
     * 
     * @param subject 科目分类
     * @return 结果
     */
    public int updateSubject(Subject subject);

    /**
     * 删除科目分类
     * 
     * @param id 科目分类主键
     * @return 结果
     */
    public int deleteSubjectById(Long id);

    /**
     * 批量删除科目分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSubjectByIds(Long[] ids);
}
