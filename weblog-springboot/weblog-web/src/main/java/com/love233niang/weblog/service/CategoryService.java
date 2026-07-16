package com.love233niang.weblog.service;

import com.love233niang.weblog.common.utils.Response;
import com.love233niang.weblog.model.vo.category.FindCategoryArticlePageListReqVO;
import com.love233niang.weblog.model.vo.category.FindCategoryListReqVO;

public interface CategoryService {
    /**
     * 获取分类列表
     *
     * @return
     */
    Response findCategoryList(FindCategoryListReqVO findCategoryListReqVO);

    /**
     * 获取分类文章分页列表
     *
     * @param findCategoryArticlePageListReqVO
     * @return
     */

    Response findCategoryArticlePageList(FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO);
}
