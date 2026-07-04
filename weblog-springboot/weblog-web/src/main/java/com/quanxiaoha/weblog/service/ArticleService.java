package com.quanxiaoha.weblog.service;

import com.quanxiaoha.weblog.common.utils.Response;
import com.quanxiaoha.weblog.model.vo.article.FindIndexArticlePageListReqVO;

public interface ArticleService {
    /**
     * 获取首页文章分页数据
     * @param findIndexArticlePageListReqVO
     * @return
     */
    Response findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO);
}
