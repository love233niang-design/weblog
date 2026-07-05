package com.love233niang.weblog.service;

import com.love233niang.weblog.common.utils.Response;
import com.love233niang.weblog.model.vo.archive.FindArchiveArticlePageListReqVO;

public interface ArchiveService {
    /**
     * 获取文章归档分页数据
     * @param findArchiveArticlePageListReqVO
     * @return
     */
    Response findArchivePageList(FindArchiveArticlePageListReqVO  findArchiveArticlePageListReqVO);
}
