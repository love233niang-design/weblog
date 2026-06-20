package com.quanxiaoha.weblog.admin.service;

import com.quanxiaoha.weblog.admin.model.vo.UpdateAdminUserPasswordReqVO;
import com.quanxiaoha.weblog.common.utils.Response;

public interface AdminUserService {
    Response updateAdminUserPassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO);
}
