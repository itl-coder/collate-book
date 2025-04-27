package com.task.collate.web.controller.errorquestion;

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
import com.task.collate.errorquestion.domain.Errorquestion;
import com.task.collate.errorquestion.service.IErrorquestionService;
import com.task.collate.common.utils.poi.ExcelUtil;
import com.task.collate.common.core.page.TableDataInfo;

/**
 * 错题Controller
 * 
 * @author collate
 * @date 2025-04-23
 */
@RestController
@RequestMapping("/errorbook/errorquestion")
public class ErrorquestionController extends BaseController
{
    @Autowired
    private IErrorquestionService errorquestionService;

    /**
     * 查询错题列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errorquestion:list')")
    @GetMapping("/list")
    public TableDataInfo list(Errorquestion errorquestion)
    {
        startPage();
        List<Errorquestion> list = errorquestionService.selectErrorquestionList(errorquestion);
        return getDataTable(list);
    }

    /**
     * 导出错题列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errorquestion:export')")
    @Log(title = "错题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Errorquestion errorquestion)
    {
        List<Errorquestion> list = errorquestionService.selectErrorquestionList(errorquestion);
        ExcelUtil<Errorquestion> util = new ExcelUtil<Errorquestion>(Errorquestion.class);
        util.exportExcel(response, list, "错题数据");
    }

    /**
     * 获取错题详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errorquestion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(errorquestionService.selectErrorquestionById(id));
    }

    /**
     * 新增错题
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errorquestion:add')")
    @Log(title = "错题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Errorquestion errorquestion)
    {
        return toAjax(errorquestionService.insertErrorquestion(errorquestion));
    }

    /**
     * 修改错题
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errorquestion:edit')")
    @Log(title = "错题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Errorquestion errorquestion)
    {
        return toAjax(errorquestionService.updateErrorquestion(errorquestion));
    }

    /**
     * 删除错题
     */
    @PreAuthorize("@ss.hasPermi('errorbook:errorquestion:remove')")
    @Log(title = "错题", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(errorquestionService.deleteErrorquestionByIds(ids));
    }
}
