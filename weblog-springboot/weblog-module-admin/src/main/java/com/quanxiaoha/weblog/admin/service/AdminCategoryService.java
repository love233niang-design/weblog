package com.quanxiaoha.weblog.admin.service;


import com.quanxiaoha.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.quanxiaoha.weblog.common.utils.Response;

public interface AdminCategoryService {
    // 添加分类
    Response addCategory(AddCategoryReqVO addCategoryReqVO);
}
