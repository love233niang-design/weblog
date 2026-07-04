package com.love233niang.weblog.admin.service;

import com.love233niang.weblog.admin.model.vo.blogsettins.UpdateBlogSettingsReqVO;
import com.love233niang.weblog.common.utils.Response;

public interface AdminBlogSettingsService {
    /**
     * 更新博客设置
     * @param updateBlogSettingsReqVO
     * @return
     */
    Response updateBlogSettins(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);

    /**
     * 获取博客设置详情
     * @return
     */
    Response findDetail();
}
