package com.task.collate.web.controller.mistake;

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
import com.task.collate.mistakeType.domain.MistakeType;
import com.task.collate.mistakeType.service.IMistakeTypeService;
import com.task.collate.common.utils.poi.ExcelUtil;
import com.task.collate.common.core.page.TableDataInfo;

/**
 * 错题类型Controller
 * 
 * @author collate
 * @date 2025-04-23
 */
@RestController
@RequestMapping("/errorbook/mistakeType")
public class MistakeTypeController extends BaseController
{
    @Autowired
    private IMistakeTypeService mistakeTypeService;

    /**
     * 查询错题类型列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:mistakeType:list')")
    @GetMapping("/list")
    public TableDataInfo list(MistakeType mistakeType)
    {
        startPage();
        List<MistakeType> list = mistakeTypeService.selectMistakeTypeList(mistakeType);
        return getDataTable(list);
    }

    /**
     * 导出错题类型列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:mistakeType:export')")
    @Log(title = "错题类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MistakeType mistakeType)
    {
        List<MistakeType> list = mistakeTypeService.selectMistakeTypeList(mistakeType);
        ExcelUtil<MistakeType> util = new ExcelUtil<MistakeType>(MistakeType.class);
        util.exportExcel(response, list, "错题类型数据");
    }

    /**
     * 获取错题类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorbook:mistakeType:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mistakeTypeService.selectMistakeTypeById(id));
    }

    /**
     * 新增错题类型
     */
    @PreAuthorize("@ss.hasPermi('errorbook:mistakeType:add')")
    @Log(title = "错题类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MistakeType mistakeType)
    {
        return toAjax(mistakeTypeService.insertMistakeType(mistakeType));
    }

    /**
     * 修改错题类型
     */
    @PreAuthorize("@ss.hasPermi('errorbook:mistakeType:edit')")
    @Log(title = "错题类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MistakeType mistakeType)
    {
        return toAjax(mistakeTypeService.updateMistakeType(mistakeType));
    }

    /**
     * 删除错题类型
     */
    @PreAuthorize("@ss.hasPermi('errorbook:mistakeType:remove')")
    @Log(title = "错题类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mistakeTypeService.deleteMistakeTypeByIds(ids));
    }
}
