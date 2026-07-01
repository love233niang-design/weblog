package com.quanxiaoha.weblog.admin.service;


import com.quanxiaoha.weblog.admin.model.vo.tag.AddTagReqVo;
import com.quanxiaoha.weblog.common.utils.Response;

public interface AdminTagService {

    /**
     * 添加标签集合
     * @param addTagReqVo
     * @return
     */
    Response addTag(AddTagReqVo addTagReqVo);
}
