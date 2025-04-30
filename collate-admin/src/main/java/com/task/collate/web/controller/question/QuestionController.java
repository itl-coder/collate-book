package com.task.collate.web.controller.question;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.task.collate.common.utils.StringUtils;
import com.task.collate.question.domain.vo.QuestionFrontVO;
import com.task.collate.question.domain.vo.QuestionVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.task.collate.common.annotation.Log;
import com.task.collate.common.core.controller.BaseController;
import com.task.collate.common.core.domain.AjaxResult;
import com.task.collate.common.enums.BusinessType;
import com.task.collate.question.domain.Question;
import com.task.collate.question.service.IQuestionService;
import com.task.collate.common.utils.poi.ExcelUtil;
import com.task.collate.common.core.page.TableDataInfo;

/**
 * 题目管理Controller
 * 
 * @author collate
 * @date 2025-04-23
 */
@RestController
@RequestMapping("/errorbook/question")
public class QuestionController extends BaseController
{
    @Autowired
    private IQuestionService questionService;

    /**
     * 查询题目管理列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:question:list')")
    @GetMapping("/list")
    public TableDataInfo list(Question question)
    {
        startPage();
        List<QuestionVO> list = questionService.selectQuestionList(question);
        return getDataTable(list);
    }


    // @PreAuthorize("@ss.hasPermi('errorbook:question:list')")
    @GetMapping("/front/list")
    public AjaxResult frontList(
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String bookName
    )
    {
        PageHelper.startPage(currentPage, pageSize);

        List<QuestionFrontVO> list  = questionService.selectFrontQuestionList(pageSize,currentPage,subjectName,bookName);
        PageInfo<QuestionFrontVO> questionFrontVOPageInfo = new PageInfo<>(list);
        return success(questionFrontVOPageInfo);
    }

    // @PreAuthorize("@ss.hasPermi('errorbook:question:list')")
    @GetMapping("/front/count/list")
    public AjaxResult countFrontList(){
      List<Question> list=  questionService.selectCountQuestion();
        return success(list);
    }
    /**
     * 导出题目管理列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:question:export')")
    @Log(title = "题目管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Question question)
    {
        List<QuestionVO> list = questionService.selectQuestionList(question);
        ExcelUtil<QuestionVO> util = new ExcelUtil<QuestionVO>(QuestionVO.class);
        util.exportExcel(response, list, "题目管理数据");
    }

    /**
     * 获取题目管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorbook:question:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(questionService.selectQuestionById(id));
    }

    /**
     * 新增题目管理
     */
    @PreAuthorize("@ss.hasPermi('errorbook:question:add')")
    @Log(title = "题目管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Question question)
    {
        return toAjax(questionService.insertQuestion(question));
    }

    /**
     * 修改题目管理
     */
    @PreAuthorize("@ss.hasPermi('errorbook:question:edit')")
    @Log(title = "题目管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Question question)
    {
        return toAjax(questionService.updateQuestion(question));
    }

    /**
     * 删除题目管理
     */
    @PreAuthorize("@ss.hasPermi('errorbook:question:remove')")
    @Log(title = "题目管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(questionService.deleteQuestionByIds(ids));
    }
}
