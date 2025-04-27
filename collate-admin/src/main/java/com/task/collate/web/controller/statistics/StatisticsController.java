package com.task.collate.web.controller.statistics;

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
import com.task.collate.statistics.domain.Statistics;
import com.task.collate.statistics.service.IStatisticsService;
import com.task.collate.common.utils.poi.ExcelUtil;
import com.task.collate.common.core.page.TableDataInfo;

/**
 * 错题统计Controller
 * 
 * @author collate
 * @date 2025-04-23
 */
@RestController
@RequestMapping("/errorbook/statistics")
public class StatisticsController extends BaseController
{
    @Autowired
    private IStatisticsService statisticsService;

    /**
     * 查询错题统计列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:statistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(Statistics statistics)
    {
        startPage();
        List<Statistics> list = statisticsService.selectStatisticsList(statistics);
        return getDataTable(list);
    }

    /**
     * 导出错题统计列表
     */
    @PreAuthorize("@ss.hasPermi('errorbook:statistics:export')")
    @Log(title = "错题统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Statistics statistics)
    {
        List<Statistics> list = statisticsService.selectStatisticsList(statistics);
        ExcelUtil<Statistics> util = new ExcelUtil<Statistics>(Statistics.class);
        util.exportExcel(response, list, "错题统计数据");
    }

    /**
     * 获取错题统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorbook:statistics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(statisticsService.selectStatisticsById(id));
    }

    /**
     * 新增错题统计
     */
    @PreAuthorize("@ss.hasPermi('errorbook:statistics:add')")
    @Log(title = "错题统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Statistics statistics)
    {
        return toAjax(statisticsService.insertStatistics(statistics));
    }

    /**
     * 修改错题统计
     */
    @PreAuthorize("@ss.hasPermi('errorbook:statistics:edit')")
    @Log(title = "错题统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Statistics statistics)
    {
        return toAjax(statisticsService.updateStatistics(statistics));
    }

    /**
     * 删除错题统计
     */
    @PreAuthorize("@ss.hasPermi('errorbook:statistics:remove')")
    @Log(title = "错题统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(statisticsService.deleteStatisticsByIds(ids));
    }
}
