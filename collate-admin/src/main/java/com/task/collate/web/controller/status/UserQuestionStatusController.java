package com.task.collate.web.controller.status;

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
import com.task.collate.status.domain.UserQuestionStatus;
import com.task.collate.status.service.IUserQuestionStatusService;
import com.task.collate.common.utils.poi.ExcelUtil;
import com.task.collate.common.core.page.TableDataInfo;

/**
 * 用户题目状态Controller
 * 
 * @author collate
 * @date 2025-04-23
 */
@RestController
@RequestMapping("/errorbook/status")
public class UserQuestionStatusController extends BaseController
{
    @Autowired
    private IUserQuestionStatusService userQuestionStatusService;

    /**
     * 查询用户题目状态列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:status:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserQuestionStatus userQuestionStatus)
    {
        startPage();
        List<UserQuestionStatus> list = userQuestionStatusService.selectUserQuestionStatusList(userQuestionStatus);
        return getDataTable(list);
    }

    /**
     * 导出用户题目状态列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:status:export')")
    @Log(title = "用户题目状态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserQuestionStatus userQuestionStatus)
    {
        List<UserQuestionStatus> list = userQuestionStatusService.selectUserQuestionStatusList(userQuestionStatus);
        ExcelUtil<UserQuestionStatus> util = new ExcelUtil<UserQuestionStatus>(UserQuestionStatus.class);
        util.exportExcel(response, list, "用户题目状态数据");
    }

    /**
     * 获取用户题目状态详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorbook:status:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(userQuestionStatusService.selectUserQuestionStatusByUserId(userId));
    }

    /**
     * 新增用户题目状态
     */
    @PreAuthorize("@ss.hasPermi('errorbook:status:add')")
    @Log(title = "用户题目状态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserQuestionStatus userQuestionStatus)
    {
        return toAjax(userQuestionStatusService.insertUserQuestionStatus(userQuestionStatus));
    }

    /**
     * 修改用户题目状态
     */
    @PreAuthorize("@ss.hasPermi('errorbook:status:edit')")
    @Log(title = "用户题目状态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserQuestionStatus userQuestionStatus)
    {
        return toAjax(userQuestionStatusService.updateUserQuestionStatus(userQuestionStatus));
    }

    /**
     * 删除用户题目状态
     */
    @PreAuthorize("@ss.hasPermi('errorbook:status:remove')")
    @Log(title = "用户题目状态", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(userQuestionStatusService.deleteUserQuestionStatusByUserIds(userIds));
    }
}
