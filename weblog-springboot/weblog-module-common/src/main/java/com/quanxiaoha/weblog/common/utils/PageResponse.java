package com.quanxiaoha.weblog.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class PageResponse<T> extends Response<List<T>> {
    // 总记录数
    private long total;

    // 每页显示的记录数，默认10
    private long size = 10L;

    // 当前页
    private long current;

    // 总页数
    private long pages;

    /**
     * 成功响应
     * @param page IPage:Mybatis Plus内置的分页接口类，以入参的方式传入，
     *             通过它拿到相关的分页数据，塞到 PageResponse 对应的字段中
     * @param data :分页数据集合
     * @return
     * @param <T>
     */
    public static <T> PageResponse<T> success(IPage page, List<T> data) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setCurrent(Objects.isNull(page) ? 1L : page.getCurrent());
        response.setSize(Objects.isNull(page) ? 10L : page.getSize());
        response.setPages(Objects.isNull(page) ? 1L : page.getPages());
        response.setTotal(Objects.isNull(page) ? 0L : page.getTotal());
        response.setData(data);
        return response;
    }
}
