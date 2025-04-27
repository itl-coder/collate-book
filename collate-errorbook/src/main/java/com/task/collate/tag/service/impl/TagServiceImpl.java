package com.task.collate.tag.service.impl;

import java.util.List;
import com.task.collate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.collate.tag.mapper.TagMapper;
import com.task.collate.tag.domain.Tag;
import com.task.collate.tag.service.ITagService;

/**
 * 错题标签Service业务层处理
 * 
 * @author collate
 * @date 2025-04-23
 */
@Service
public class TagServiceImpl implements ITagService 
{
    @Autowired
    private TagMapper tagMapper;

    /**
     * 查询错题标签
     * 
     * @param id 错题标签主键
     * @return 错题标签
     */
    @Override
    public Tag selectTagById(Long id)
    {
        return tagMapper.selectTagById(id);
    }

    /**
     * 查询错题标签列表
     * 
     * @param tag 错题标签
     * @return 错题标签
     */
    @Override
    public List<Tag> selectTagList(Tag tag)
    {
        return tagMapper.selectTagList(tag);
    }

    /**
     * 新增错题标签
     * 
     * @param tag 错题标签
     * @return 结果
     */
    @Override
    public int insertTag(Tag tag)
    {
        tag.setCreateTime(DateUtils.getNowDate());
        return tagMapper.insertTag(tag);
    }

    /**
     * 修改错题标签
     * 
     * @param tag 错题标签
     * @return 结果
     */
    @Override
    public int updateTag(Tag tag)
    {
        tag.setUpdateTime(DateUtils.getNowDate());
        return tagMapper.updateTag(tag);
    }

    /**
     * 批量删除错题标签
     * 
     * @param ids 需要删除的错题标签主键
     * @return 结果
     */
    @Override
    public int deleteTagByIds(Long[] ids)
    {
        return tagMapper.deleteTagByIds(ids);
    }

    /**
     * 删除错题标签信息
     * 
     * @param id 错题标签主键
     * @return 结果
     */
    @Override
    public int deleteTagById(Long id)
    {
        return tagMapper.deleteTagById(id);
    }
}
