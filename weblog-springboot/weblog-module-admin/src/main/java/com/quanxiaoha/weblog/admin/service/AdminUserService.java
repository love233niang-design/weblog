package com.quanxiaoha.weblog.admin.service;

import com.quanxiaoha.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.quanxiaoha.weblog.common.utils.Response;

public interface AdminUserService {
    // 修改密码
    Response updateAdminUserPassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO);

    // 获取当前登录的用户信息
    Response findUserInfo();
}
