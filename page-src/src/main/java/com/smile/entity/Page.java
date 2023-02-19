package com.smile.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @ClassName Page
 * @Author smile
 * @date 2023.02.18 21:43
 */
public class Page<T> {

    /**
     * 页号
     */
    private int pageStart = 1;
    /**
     * 页面大小
     */
    private int pageSize = 10;
    /**
     * 记录总数
     */
    private int totalSize;
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 记录数
     */
    private List<T> records;
    /**
     * 其他参数
     */
    private Map<String, Object> params = new HashMap<>();

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageStart=" + pageStart +
                ", pageSize=" + pageSize +
                ", totalSize=" + totalSize +
                ", totalPages=" + totalPages +
                ", records=" + records +
                ", params=" + params +
                '}';
    }
}
