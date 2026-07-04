package com.quanxiaoha.weblog.service;

import com.quanxiaoha.weblog.common.utils.Response;

public interface TagService {
    /**
     * 获取标签列表
     * @return
     */
    Response findTagList();
}