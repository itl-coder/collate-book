package com.task.collate.book.mapper;


import com.task.collate.book.domain.Book;
import com.task.collate.common.core.domain.AjaxResult;

import java.util.List;

/**
 * 教辅分类Mapper接口
 *
 * @author collate
 * @date 2025-04-23
 */
public interface BookMapper {
    /**
     * 查询教辅分类
     *
     * @param id 教辅分类主键
     * @return 教辅分类
     */
    public Book selectBookById(Long id);

    /**
     * 查询教辅分类列表
     *
     * @param book 教辅分类
     * @return 教辅分类集合
     */
    public List<Book> selectBookList(Book book);

    /**
     * 新增教辅分类
     *
     * @param book 教辅分类
     * @return 结果
     */
    public int insertBook(Book book);

    /**
     * 修改教辅分类
     *
     * @param book 教辅分类
     * @return 结果
     */
    public int updateBook(Book book);

    /**
     * 删除教辅分类
     *
     * @param id 教辅分类主键
     * @return 结果
     */
    public int deleteBookById(Long id);

    /**
     * 批量删除教辅分类
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookByIds(Long[] ids);

    public List<Book> selectCountBookList();
}
