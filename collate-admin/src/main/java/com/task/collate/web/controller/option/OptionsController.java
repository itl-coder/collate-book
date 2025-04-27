package com.task.collate.web.controller.option;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.task.collate.common.annotation.Log;
import com.task.collate.common.core.controller.BaseController;
import com.task.collate.common.core.domain.AjaxResult;
import com.task.collate.common.enums.BusinessType;
import com.task.collate.option.domain.Options;
import com.task.collate.option.service.IOptionsService;
import com.task.collate.common.utils.poi.ExcelUtil;
import com.task.collate.common.core.page.TableDataInfo;

/**
 * 选项，用于存储每道题的选项及其是否为正确答案Controller
 * 
 * @author collate
 * @date 2025-04-25
 */
@RestController
@RequestMapping("/errorbook/option")
public class OptionsController extends BaseController
{
    @Autowired
    private IOptionsService optionsService;

    /**
     * 查询选项，用于存储每道题的选项及其是否为正确答案列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:option:list')")
    @GetMapping("/list")
    public TableDataInfo list(Options options)
    {
        startPage();
        List<Options> list = optionsService.selectOptionsList(options);
        return getDataTable(list);
    }

    /**
     * 导出选项，用于存储每道题的选项及其是否为正确答案列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:option:export')")
    @Log(title = "选项，用于存储每道题的选项及其是否为正确答案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Options options)
    {
        List<Options> list = optionsService.selectOptionsList(options);
        ExcelUtil<Options> util = new ExcelUtil<Options>(Options.class);
        util.exportExcel(response, list, "选项，用于存储每道题的选项及其是否为正确答案数据");
    }

    /**
     * 获取选项，用于存储每道题的选项及其是否为正确答案详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorbook:option:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(optionsService.selectOptionsById(id));
    }

    /**
     * 新增选项，用于存储每道题的选项及其是否为正确答案
     */
    @PreAuthorize("@ss.hasPermi('errorbook:option:add')")
    @Log(title = "选项，用于存储每道题的选项及其是否为正确答案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Options options)
    {
        return toAjax(optionsService.insertOptions(options));
    }

    /**
     * 修改选项，用于存储每道题的选项及其是否为正确答案
     */
    @PreAuthorize("@ss.hasPermi('errorbook:option:edit')")
    @Log(title = "选项，用于存储每道题的选项及其是否为正确答案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Options options)
    {
        return toAjax(optionsService.updateOptions(options));
    }

    /**
     * 删除选项，用于存储每道题的选项及其是否为正确答案
     */
    @PreAuthorize("@ss.hasPermi('errorbook:option:remove')")
    @Log(title = "选项，用于存储每道题的选项及其是否为正确答案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(optionsService.deleteOptionsByIds(ids));
    }
}
