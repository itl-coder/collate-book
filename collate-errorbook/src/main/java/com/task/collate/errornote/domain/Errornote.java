package com.task.collate.errornote.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.task.collate.common.annotation.Excel;
import com.task.collate.common.core.domain.BaseEntity;

/**
 * 错题笔记对象 errornote
 * 
 * @author collate
 * @date 2025-04-23
 */
public class Errornote extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 笔记主键 */
    private Long id;

    /** 关联错题ID */
    @Excel(name = "关联错题ID")
    private Long errorquestionId;

    /** 笔记内容 */
    @Excel(name = "笔记内容")
    private String noteContent;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setErrorquestionId(Long errorquestionId) 
    {
        this.errorquestionId = errorquestionId;
    }

    public Long getErrorquestionId() 
    {
        return errorquestionId;
    }
    public void setNoteContent(String noteContent) 
    {
        this.noteContent = noteContent;
    }

    public String getNoteContent() 
    {
        return noteContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("errorquestionId", getErrorquestionId())
            .append("noteContent", getNoteContent())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
