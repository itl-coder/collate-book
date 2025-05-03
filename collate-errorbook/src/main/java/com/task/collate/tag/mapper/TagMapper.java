package com.task.collate.tag.mapper;

import com.task.collate.tag.domain.Tag;

import java.util.List;

/**
 * 错题标签Mapper接口
 *
 * @author collate
 * @date 2025-04-23
 */
public interface TagMapper {
    /**
     * 查询错题标签
     *
     * @param id 错题标签主键
     * @return 错题标签
     */
    public Tag selectTagById(Long id);

    /**
     * 查询错题标签列表
     *
     * @param tag 错题标签
     * @return 错题标签集合
     */
    public List<Tag> selectTagList(Tag tag);

    /**
     * 新增错题标签
     *
     * @param tag 错题标签
     * @return 结果
     */
    public int insertTag(Tag tag);

    /**
     * 修改错题标签
     *
     * @param tag 错题标签
     * @return 结果
     */
    public int updateTag(Tag tag);

    /**
     * 删除错题标签
     *
     * @param id 错题标签主键
     * @return 结果
     */
    public int deleteTagById(Long id);

    /**
     * 批量删除错题标签
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTagByIds(Long[] ids);

    public List<Tag> selectCountTagList();
}
