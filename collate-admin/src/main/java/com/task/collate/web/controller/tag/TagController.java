package com.task.collate.web.controller.tag;

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
import com.task.collate.tag.domain.Tag;
import com.task.collate.tag.service.ITagService;
import com.task.collate.common.utils.poi.ExcelUtil;
import com.task.collate.common.core.page.TableDataInfo;

/**
 * 错题标签Controller
 * 
 * @author collate
 * @date 2025-04-23
 */
@RestController
@RequestMapping("/errorbook/tag")
public class TagController extends BaseController
{
    @Autowired
    private ITagService tagService;

    /**
     * 查询错题标签列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(Tag tag)
    {
        startPage();
        List<Tag> list = tagService.selectTagList(tag);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('errorbook:tag:list')")
    @GetMapping("/count/list")
    public AjaxResult frontCountTagList()
    {
        List<Tag> list = tagService.selectCountTagList();
        return success(list);
    }

    /**
     * 导出错题标签列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:tag:export')")
    @Log(title = "错题标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Tag tag)
    {
        List<Tag> list = tagService.selectTagList(tag);
        ExcelUtil<Tag> util = new ExcelUtil<Tag>(Tag.class);
        util.exportExcel(response, list, "错题标签数据");
    }

    /**
     * 获取错题标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorbook:tag:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tagService.selectTagById(id));
    }

    /**
     * 新增错题标签
     */
    @PreAuthorize("@ss.hasPermi('errorbook:tag:add')")
    @Log(title = "错题标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Tag tag)
    {
        return toAjax(tagService.insertTag(tag));
    }

    /**
     * 修改错题标签
     */
    @PreAuthorize("@ss.hasPermi('errorbook:tag:edit')")
    @Log(title = "错题标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Tag tag)
    {
        return toAjax(tagService.updateTag(tag));
    }

    /**
     * 删除错题标签
     */
    @PreAuthorize("@ss.hasPermi('errorbook:tag:remove')")
    @Log(title = "错题标签", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tagService.deleteTagByIds(ids));
    }
}
