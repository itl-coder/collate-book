package com.task.collate.web.controller.book;


import com.task.collate.common.annotation.Log;
import com.task.collate.common.core.controller.BaseController;
import com.task.collate.common.core.domain.AjaxResult;
import com.task.collate.common.core.page.TableDataInfo;
import com.task.collate.common.enums.BusinessType;
import com.task.collate.common.utils.poi.ExcelUtil;
import com.task.collate.book.domain.Book;
import com.task.collate.book.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 教辅分类Controller
 *
 * @author collate
 * @date 2025-04-23
 */
@RestController
@RequestMapping("/errorbook/book")
public class BookController extends BaseController {
    @Autowired
    private IBookService bookService;

    /**
     * 查询教辅分类列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:book:list')")
    @GetMapping("/list")
    public TableDataInfo list(Book book) {
        startPage();
        List<Book> list = bookService.selectBookList(book);
        return getDataTable(list);
    }

    /**
     * 导出教辅分类列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:book:export')")
    @Log(title = "教辅分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Book book) {
        List<Book> list = bookService.selectBookList(book);
        ExcelUtil<Book> util = new ExcelUtil<Book>(Book.class);
        util.exportExcel(response, list, "教辅分类数据");
    }

    /**
     * 获取教辅分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorbook:book:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bookService.selectBookById(id));
    }

    /**
     * 新增教辅分类
     */
    @PreAuthorize("@ss.hasPermi('errorbook:book:add')")
    @Log(title = "教辅分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Book book) {
        return toAjax(bookService.insertBook(book));
    }

    /**
     * 修改教辅分类
     */
    @PreAuthorize("@ss.hasPermi('errorbook:book:edit')")
    @Log(title = "教辅分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Book book) {
        return toAjax(bookService.updateBook(book));
    }

    /**
     * 删除教辅分类
     */
    @PreAuthorize("@ss.hasPermi('errorbook:book:remove')")
    @Log(title = "教辅分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bookService.deleteBookByIds(ids));
    }
}
