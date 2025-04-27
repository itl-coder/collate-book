package com.task.collate.option.mapper;

import java.util.List;
import com.task.collate.option.domain.Options;

/**
 * 选项，用于存储每道题的选项及其是否为正确答案Mapper接口
 * 
 * @author collate
 * @date 2025-04-25
 */
public interface OptionsMapper 
{
    /**
     * 查询选项，用于存储每道题的选项及其是否为正确答案
     * 
     * @param id 选项，用于存储每道题的选项及其是否为正确答案主键
     * @return 选项，用于存储每道题的选项及其是否为正确答案
     */
    public Options selectOptionsById(Long id);

    /**
     * 查询选项，用于存储每道题的选项及其是否为正确答案列表
     * 
     * @param options 选项，用于存储每道题的选项及其是否为正确答案
     * @return 选项，用于存储每道题的选项及其是否为正确答案集合
     */
    public List<Options> selectOptionsList(Options options);

    /**
     * 新增选项，用于存储每道题的选项及其是否为正确答案
     * 
     * @param options 选项，用于存储每道题的选项及其是否为正确答案
     * @return 结果
     */
    public int insertOptions(Options options);

    /**
     * 修改选项，用于存储每道题的选项及其是否为正确答案
     * 
     * @param options 选项，用于存储每道题的选项及其是否为正确答案
     * @return 结果
     */
    public int updateOptions(Options options);

    /**
     * 删除选项，用于存储每道题的选项及其是否为正确答案
     * 
     * @param id 选项，用于存储每道题的选项及其是否为正确答案主键
     * @return 结果
     */
    public int deleteOptionsById(Long id);

    /**
     * 批量删除选项，用于存储每道题的选项及其是否为正确答案
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOptionsByIds(Long[] ids);
}
