package com.task.collate.web.controller.subject;

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
import com.task.collate.subject.domain.Subject;
import com.task.collate.subject.service.ISubjectService;
import com.task.collate.common.utils.poi.ExcelUtil;
import com.task.collate.common.core.page.TableDataInfo;

/**
 * 科目分类Controller
 * 
 * @author collate
 * @date 2025-04-23
 */
@RestController
@RequestMapping("/errorbook/subject")
public class SubjectController extends BaseController
{
    @Autowired
    private ISubjectService subjectService;

    /**
     * 查询科目分类列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:subject:list')")
    @GetMapping("/list")
    public TableDataInfo list(Subject subject)
    {
        startPage();
        List<Subject> list = subjectService.selectSubjectList(subject);
        return getDataTable(list);
    }

    /**
     * 查询科目分类列表
     */
    // @PreAuthorize("@ss.hasPermi('errorbook:subject:list')")
    @GetMapping("/count/list")
    public AjaxResult listSubjectCount(){
     List<Subject> subjectList =  subjectService.countSubjectList();
     return success(subjectList);
    }
    /**
     * 导出科目分类列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:subject:export')")
    @Log(title = "科目分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Subject subject)
    {
        List<Subject> list = subjectService.selectSubjectList(subject);
        ExcelUtil<Subject> util = new ExcelUtil<Subject>(Subject.class);
        util.exportExcel(response, list, "科目分类数据");
    }

    /**
     * 获取科目分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorbook:subject:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(subjectService.selectSubjectById(id));
    }

    /**
     * 新增科目分类
     */
    @PreAuthorize("@ss.hasPermi('errorbook:subject:add')")
    @Log(title = "科目分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Subject subject)
    {
        return toAjax(subjectService.insertSubject(subject));
    }

    /**
     * 修改科目分类
     */
    @PreAuthorize("@ss.hasPermi('errorbook:subject:edit')")
    @Log(title = "科目分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Subject subject)
    {
        return toAjax(subjectService.updateSubject(subject));
    }

    /**
     * 删除科目分类
     */
    @PreAuthorize("@ss.hasPermi('errorbook:subject:remove')")
    @Log(title = "科目分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(subjectService.deleteSubjectByIds(ids));
    }
}
