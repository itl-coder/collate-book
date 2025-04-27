package com.task.collate.mistakeType.service.impl;

import java.util.List;
import com.task.collate.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.collate.mistakeType.mapper.MistakeTypeMapper;
import com.task.collate.mistakeType.domain.MistakeType;
import com.task.collate.mistakeType.service.IMistakeTypeService;

/**
 * 错题类型Service业务层处理
 * 
 * @author collate
 * @date 2025-04-23
 */
@Service
public class MistakeTypeServiceImpl implements IMistakeTypeService 
{
    @Autowired
    private MistakeTypeMapper mistakeTypeMapper;

    /**
     * 查询错题类型
     * 
     * @param id 错题类型主键
     * @return 错题类型
     */
    @Override
    public MistakeType selectMistakeTypeById(Long id)
    {
        return mistakeTypeMapper.selectMistakeTypeById(id);
    }

    /**
     * 查询错题类型列表
     * 
     * @param mistakeType 错题类型
     * @return 错题类型
     */
    @Override
    public List<MistakeType> selectMistakeTypeList(MistakeType mistakeType)
    {
        return mistakeTypeMapper.selectMistakeTypeList(mistakeType);
    }

    /**
     * 新增错题类型
     * 
     * @param mistakeType 错题类型
     * @return 结果
     */
    @Override
    public int insertMistakeType(MistakeType mistakeType)
    {
        mistakeType.setCreateTime(DateUtils.getNowDate());
        return mistakeTypeMapper.insertMistakeType(mistakeType);
    }

    /**
     * 修改错题类型
     * 
     * @param mistakeType 错题类型
     * @return 结果
     */
    @Override
    public int updateMistakeType(MistakeType mistakeType)
    {
        mistakeType.setUpdateTime(DateUtils.getNowDate());
        return mistakeTypeMapper.updateMistakeType(mistakeType);
    }

    /**
     * 批量删除错题类型
     * 
     * @param ids 需要删除的错题类型主键
     * @return 结果
     */
    @Override
    public int deleteMistakeTypeByIds(Long[] ids)
    {
        return mistakeTypeMapper.deleteMistakeTypeByIds(ids);
    }

    /**
     * 删除错题类型信息
     * 
     * @param id 错题类型主键
     * @return 结果
     */
    @Override
    public int deleteMistakeTypeById(Long id)
    {
        return mistakeTypeMapper.deleteMistakeTypeById(id);
    }
}
