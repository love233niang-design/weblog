package com.quanxiaoha.weblog.admin.service;


import com.quanxiaoha.weblog.admin.model.vo.tag.AddTagReqVo;
import com.quanxiaoha.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.quanxiaoha.weblog.common.utils.PageResponse;
import com.quanxiaoha.weblog.common.utils.Response;

public interface AdminTagService {

    /**
     * 添加标签集合
     * @param addTagReqVo
     * @return
     */
    Response addTag(AddTagReqVo addTagReqVo);

    /**
     * 标签分页列表查询
     * @param findTagPageListReqVO
     * @return
     */
    PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO);
}
