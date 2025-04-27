package com.task.collate.book.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.task.collate.common.annotation.Excel;
import com.task.collate.common.core.domain.BaseEntity;

/**
 * 教辅分类对象 book
 * 
 * @author collate
 * @date 2025-04-23
 */
public class Book extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 教辅主键 */
    private Long id;

    /** 教辅名称，唯一 */
    @Excel(name = "教辅名称，唯一")
    private String bookName;

    /** 所属科目ID，可为空 */
    @Excel(name = "所属科目ID，可为空")
    private Long subjectId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBookName(String bookName) 
    {
        this.bookName = bookName;
    }

    public String getBookName() 
    {
        return bookName;
    }
    public void setSubjectId(Long subjectId) 
    {
        this.subjectId = subjectId;
    }

    public Long getSubjectId() 
    {
        return subjectId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bookName", getBookName())
            .append("subjectId", getSubjectId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
