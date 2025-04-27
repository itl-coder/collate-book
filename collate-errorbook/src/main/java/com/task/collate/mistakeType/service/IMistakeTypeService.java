package com.task.collate.mistakeType.service;

import java.util.List;
import com.task.collate.mistakeType.domain.MistakeType;

/**
 * 错题类型Service接口
 * 
 * @author collate
 * @date 2025-04-23
 */
public interface IMistakeTypeService 
{
    /**
     * 查询错题类型
     * 
     * @param id 错题类型主键
     * @return 错题类型
     */
    public MistakeType selectMistakeTypeById(Long id);

    /**
     * 查询错题类型列表
     * 
     * @param mistakeType 错题类型
     * @return 错题类型集合
     */
    public List<MistakeType> selectMistakeTypeList(MistakeType mistakeType);

    /**
     * 新增错题类型
     * 
     * @param mistakeType 错题类型
     * @return 结果
     */
    public int insertMistakeType(MistakeType mistakeType);

    /**
     * 修改错题类型
     * 
     * @param mistakeType 错题类型
     * @return 结果
     */
    public int updateMistakeType(MistakeType mistakeType);

    /**
     * 批量删除错题类型
     * 
     * @param ids 需要删除的错题类型主键集合
     * @return 结果
     */
    public int deleteMistakeTypeByIds(Long[] ids);

    /**
     * 删除错题类型信息
     * 
     * @param id 错题类型主键
     * @return 结果
     */
    public int deleteMistakeTypeById(Long id);
}
