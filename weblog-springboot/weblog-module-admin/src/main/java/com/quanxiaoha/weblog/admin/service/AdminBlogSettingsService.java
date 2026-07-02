package com.quanxiaoha.weblog.admin.service;

import com.quanxiaoha.weblog.admin.model.vo.blogsettins.UpdateBlogSettingsReqVO;
import com.quanxiaoha.weblog.common.utils.Response;

public interface AdminBlogSettingsService {
    Response updateBlogSettins(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);
}
