package com.task.collate.book.service;

import java.util.List;
import com.task.collate.book.domain.Book;

/**
 * 教辅分类Service接口
 * 
 * @author collate
 * @date 2025-04-23
 */
public interface IBookService 
{
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
     * 批量删除教辅分类
     * 
     * @param ids 需要删除的教辅分类主键集合
     * @return 结果
     */
    public int deleteBookByIds(Long[] ids);

    /**
     * 删除教辅分类信息
     * 
     * @param id 教辅分类主键
     * @return 结果
     */
    public int deleteBookById(Long id);
}
