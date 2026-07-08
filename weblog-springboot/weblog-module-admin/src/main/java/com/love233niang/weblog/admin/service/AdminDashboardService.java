package com.love233niang.weblog.admin.service;

import com.love233niang.weblog.common.utils.Response;

public interface AdminDashboardService {
    /**
     * 获取仪表盘基础统计信息
     *
     * @return
     */
    Response findDashboardStatistics();
}
