package com.love233niang.weblog.service.impl;

import com.love233niang.weblog.common.domain.dos.BlogSettingsDO;
import com.love233niang.weblog.common.domain.mapper.BlogSettingsMapper;
import com.love233niang.weblog.common.utils.Response;
import com.love233niang.weblog.convert.BlogSettingsConvert;
import com.love233niang.weblog.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import com.love233niang.weblog.service.BlogSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlogSettingsServiceImpl implements BlogSettingsService {
    @Autowired
    private BlogSettingsMapper blogSettingsMapper;

    /**
     * 获取博客设置信息
     *
     * @return
     */
    @Override
    public Response findDetail() {
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        // DO 转 VO
        FindBlogSettingsDetailRspVO vo = BlogSettingsConvert.INSTANCE.convertDO2VO(blogSettingsDO);
        return Response.success(vo);
    }
}
