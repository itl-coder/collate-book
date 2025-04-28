package com.task.collate.book.service.impl;

import com.task.collate.common.core.domain.AjaxResult;
import com.task.collate.common.utils.DateUtils;
import com.task.collate.book.domain.Book;
import com.task.collate.book.mapper.BookMapper;
import com.task.collate.book.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教辅分类Service业务层处理
 *
 * @author collate
 * @date 2025-04-23
 */
@Service
public class BookServiceImpl implements IBookService {
    @Autowired
    private BookMapper bookMapper;

    /**
     * 查询教辅分类
     *
     * @param id 教辅分类主键
     * @return 教辅分类
     */
    @Override
    public Book selectBookById(Long id) {
        return bookMapper.selectBookById(id);
    }

    /**
     * 查询教辅分类列表
     *
     * @param book 教辅分类
     * @return 教辅分类
     */
    @Override
    public List<Book> selectBookList(Book book) {
        return bookMapper.selectBookList(book);
    }

    /**
     * 新增教辅分类
     *
     * @param book 教辅分类
     * @return 结果
     */
    @Override
    public int insertBook(Book book) {
        book.setCreateTime(DateUtils.getNowDate());
        return bookMapper.insertBook(book);
    }

    /**
     * 修改教辅分类
     *
     * @param book 教辅分类
     * @return 结果
     */
    @Override
    public int updateBook(Book book) {
        book.setUpdateTime(DateUtils.getNowDate());
        return bookMapper.updateBook(book);
    }

    /**
     * 批量删除教辅分类
     *
     * @param ids 需要删除的教辅分类主键
     * @return 结果
     */
    @Override
    public int deleteBookByIds(Long[] ids) {
        return bookMapper.deleteBookByIds(ids);
    }

    /**
     * 删除教辅分类信息
     *
     * @param id 教辅分类主键
     * @return 结果
     */
    @Override
    public int deleteBookById(Long id) {
        return bookMapper.deleteBookById(id);
    }

    @Override
    public  List<Book> selectCountBookList() {
        return bookMapper.selectCountBookList();
    }
}
