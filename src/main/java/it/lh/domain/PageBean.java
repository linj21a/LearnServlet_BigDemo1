package it.lh.domain;

import java.util.List;

/**
 * 分页查询封装的对象
 */

public class PageBean<T> {
    private int totalCount;//总的记录数
    private int currentPage;//当前页码
    private int totalPages;//总的页数
    private int row;//每页显示多少数据
    private List<T> list;//每页显示的数据集合

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", row=" + row +
                ", list=" + list +
                '}';
    }
}

