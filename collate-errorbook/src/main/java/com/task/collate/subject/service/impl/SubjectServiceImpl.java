package com.task.collate.subject.service.impl;

import java.util.List;
import com.task.collate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.collate.subject.mapper.SubjectMapper;
import com.task.collate.subject.domain.Subject;
import com.task.collate.subject.service.ISubjectService;

/**
 * 科目分类Service业务层处理
 * 
 * @author collate
 * @date 2025-04-23
 */
@Service
public class SubjectServiceImpl implements ISubjectService 
{
    @Autowired
    private SubjectMapper subjectMapper;

    /**
     * 查询科目分类
     * 
     * @param id 科目分类主键
     * @return 科目分类
     */
    @Override
    public Subject selectSubjectById(Long id)
    {
        return subjectMapper.selectSubjectById(id);
    }

    /**
     * 查询科目分类列表
     * 
     * @param subject 科目分类
     * @return 科目分类
     */
    @Override
    public List<Subject> selectSubjectList(Subject subject)
    {
        return subjectMapper.selectSubjectList(subject);
    }

    /**
     * 新增科目分类
     * 
     * @param subject 科目分类
     * @return 结果
     */
    @Override
    public int insertSubject(Subject subject)
    {
        subject.setCreateTime(DateUtils.getNowDate());
        return subjectMapper.insertSubject(subject);
    }

    /**
     * 修改科目分类
     * 
     * @param subject 科目分类
     * @return 结果
     */
    @Override
    public int updateSubject(Subject subject)
    {
        subject.setUpdateTime(DateUtils.getNowDate());
        return subjectMapper.updateSubject(subject);
    }

    /**
     * 批量删除科目分类
     * 
     * @param ids 需要删除的科目分类主键
     * @return 结果
     */
    @Override
    public int deleteSubjectByIds(Long[] ids)
    {
        return subjectMapper.deleteSubjectByIds(ids);
    }

    /**
     * 删除科目分类信息
     * 
     * @param id 科目分类主键
     * @return 结果
     */
    @Override
    public int deleteSubjectById(Long id)
    {
        return subjectMapper.deleteSubjectById(id);
    }

    @Override
    public List<Subject> countSubjectList() {
        return subjectMapper.countSubjectList();
    }
}
