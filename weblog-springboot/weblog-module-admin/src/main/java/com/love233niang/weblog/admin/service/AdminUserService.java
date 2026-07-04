package com.love233niang.weblog.admin.service;

import com.love233niang.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.love233niang.weblog.common.utils.Response;

public interface AdminUserService {
    // 修改密码
    Response updateAdminUserPassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO);

    // 获取当前登录的用户信息
    Response findUserInfo();
}
