package com.quanxiaoha.weblog.admin.convert;

import com.quanxiaoha.weblog.admin.model.vo.blogsettins.FindBlogSettingsRspVO;
import com.quanxiaoha.weblog.admin.model.vo.blogsettins.UpdateBlogSettingsReqVO;
import com.quanxiaoha.weblog.common.domain.dos.BlogSettingsDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {
    // 初始化 convert 实例
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);


    // 将 VO 转化为 DO
    BlogSettingsDO convertVO2DO(UpdateBlogSettingsReqVO beam);

    //将 DO 转化为 VO
    FindBlogSettingsRspVO convertDO2VO(BlogSettingsDO blogSettingsDO);
}
