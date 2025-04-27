package com.task.collate.option.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.task.collate.option.mapper.OptionsMapper;
import com.task.collate.option.domain.Options;
import com.task.collate.option.service.IOptionsService;

/**
 * 选项，用于存储每道题的选项及其是否为正确答案Service业务层处理
 * 
 * @author collate
 * @date 2025-04-25
 */
@Service
public class OptionsServiceImpl implements IOptionsService 
{
    @Autowired
    private OptionsMapper optionsMapper;

    /**
     * 查询选项，用于存储每道题的选项及其是否为正确答案
     * 
     * @param id 选项，用于存储每道题的选项及其是否为正确答案主键
     * @return 选项，用于存储每道题的选项及其是否为正确答案
     */
    @Override
    public Options selectOptionsById(Long id)
    {
        return optionsMapper.selectOptionsById(id);
    }

    /**
     * 查询选项，用于存储每道题的选项及其是否为正确答案列表
     * 
     * @param options 选项，用于存储每道题的选项及其是否为正确答案
     * @return 选项，用于存储每道题的选项及其是否为正确答案
     */
    @Override
    public List<Options> selectOptionsList(Options options)
    {
        return optionsMapper.selectOptionsList(options);
    }

    /**
     * 新增选项，用于存储每道题的选项及其是否为正确答案
     * 
     * @param options 选项，用于存储每道题的选项及其是否为正确答案
     * @return 结果
     */
    @Override
    public int insertOptions(Options options)
    {
        return optionsMapper.insertOptions(options);
    }

    /**
     * 修改选项，用于存储每道题的选项及其是否为正确答案
     * 
     * @param options 选项，用于存储每道题的选项及其是否为正确答案
     * @return 结果
     */
    @Override
    public int updateOptions(Options options)
    {
        return optionsMapper.updateOptions(options);
    }

    /**
     * 批量删除选项，用于存储每道题的选项及其是否为正确答案
     * 
     * @param ids 需要删除的选项，用于存储每道题的选项及其是否为正确答案主键
     * @return 结果
     */
    @Override
    public int deleteOptionsByIds(Long[] ids)
    {
        return optionsMapper.deleteOptionsByIds(ids);
    }

    /**
     * 删除选项，用于存储每道题的选项及其是否为正确答案信息
     * 
     * @param id 选项，用于存储每道题的选项及其是否为正确答案主键
     * @return 结果
     */
    @Override
    public int deleteOptionsById(Long id)
    {
        return optionsMapper.deleteOptionsById(id);
    }
}
