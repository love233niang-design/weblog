package com.love233niang.weblog.admin.service;


import com.love233niang.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.love233niang.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.love233niang.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.love233niang.weblog.common.utils.PageResponse;
import com.love233niang.weblog.common.utils.Response;

public interface AdminCategoryService {
    // 添加分类
    Response addCategory(AddCategoryReqVO addCategoryReqVO);

    // 分类分页数据查询
    PageResponse findCategoryPageList(FindCategoryPageListReqVO findCategoryPageListReqVO);

    // 删除分类
    Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO);

    // 获取文章分类 Select 列表数据
    Response findCategorySelectList();
}
