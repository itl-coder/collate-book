package com.task.collate.web.controller.answer;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.task.collate.answer.domain.vo.UserAnswerVO;
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
import com.task.collate.answer.domain.UserAnswer;
import com.task.collate.answer.service.IUserAnswerService;
import com.task.collate.common.utils.poi.ExcelUtil;
import com.task.collate.common.core.page.TableDataInfo;

/**
 * 用户作答Controller
 * 
 * @author collate
 * @date 2025-04-28
 */
@RestController
@RequestMapping("/errorbook/answer")
public class UserAnswerController extends BaseController
{
    @Autowired
    private IUserAnswerService userAnswerService;

    /**
     * 查询用户作答列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:answer:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserAnswer userAnswer)
    {
        startPage();
        List<UserAnswer> list = userAnswerService.selectUserAnswerList(userAnswer);
        return getDataTable(list);
    }

    /**
     * 查询用户作答列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:answer:list')")
    @GetMapping("/front/list")
    public AjaxResult frontLoginUserList(Integer userId,String subjectName)
    {
        List<UserAnswerVO> list = userAnswerService.selectFrontUserAnswerList(userId,subjectName);
        return success(list);
    }


    /**
     * 导出用户作答列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:answer:export')")
    @Log(title = "用户作答", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserAnswer userAnswer)
    {
        List<UserAnswer> list = userAnswerService.selectUserAnswerList(userAnswer);
        ExcelUtil<UserAnswer> util = new ExcelUtil<UserAnswer>(UserAnswer.class);
        util.exportExcel(response, list, "用户作答数据");
    }

    /**
     * 获取用户作答详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorbook:answer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userAnswerService.selectUserAnswerById(id));
    }

    /**
     * 新增用户作答
     */
    @PreAuthorize("@ss.hasPermi('errorbook:answer:add')")
    @Log(title = "用户作答", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserAnswer userAnswer)
    {
        return toAjax(userAnswerService.insertUserAnswer(userAnswer));
    }

    /**
     * 修改用户作答
     */
    @PreAuthorize("@ss.hasPermi('errorbook:answer:edit')")
    @Log(title = "用户作答", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserAnswer userAnswer)
    {
        return toAjax(userAnswerService.updateUserAnswer(userAnswer));
    }

    /**
     * 删除用户作答
     */
    @PreAuthorize("@ss.hasPermi('errorbook:answer:remove')")
    @Log(title = "用户作答", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userAnswerService.deleteUserAnswerByIds(ids));
    }
}
