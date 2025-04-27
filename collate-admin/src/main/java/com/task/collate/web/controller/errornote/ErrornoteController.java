package com.task.collate.web.controller.errornote;

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
import com.task.collate.errornote.domain.Errornote;
import com.task.collate.errornote.service.IErrornoteService;
import com.task.collate.common.utils.poi.ExcelUtil;
import com.task.collate.common.core.page.TableDataInfo;

/**
 * 错题笔记Controller
 * 
 * @author collate
 * @date 2025-04-23
 */
@RestController
@RequestMapping("/errorbook/errornote")
public class ErrornoteController extends BaseController
{
    @Autowired
    private IErrornoteService errornoteService;

    /**
     * 查询错题笔记列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errornote:list')")
    @GetMapping("/list")
    public TableDataInfo list(Errornote errornote)
    {
        startPage();
        List<Errornote> list = errornoteService.selectErrornoteList(errornote);
        return getDataTable(list);
    }

    /**
     * 导出错题笔记列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errornote:export')")
    @Log(title = "错题笔记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Errornote errornote)
    {
        List<Errornote> list = errornoteService.selectErrornoteList(errornote);
        ExcelUtil<Errornote> util = new ExcelUtil<Errornote>(Errornote.class);
        util.exportExcel(response, list, "错题笔记数据");
    }

    /**
     * 获取错题笔记详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errornote:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(errornoteService.selectErrornoteById(id));
    }

    /**
     * 新增错题笔记
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errornote:add')")
    @Log(title = "错题笔记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Errornote errornote)
    {
        return toAjax(errornoteService.insertErrornote(errornote));
    }

    /**
     * 修改错题笔记
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errornote:edit')")
    @Log(title = "错题笔记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Errornote errornote)
    {
        return toAjax(errornoteService.updateErrornote(errornote));
    }

    /**
     * 删除错题笔记
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errornote:remove')")
    @Log(title = "错题笔记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(errornoteService.deleteErrornoteByIds(ids));
    }
}
