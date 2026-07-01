package com.quanxiaoha.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quanxiaoha.weblog.admin.model.vo.tag.AddTagReqVo;
import com.quanxiaoha.weblog.admin.service.AdminTagService;
import com.quanxiaoha.weblog.common.domain.dos.TagDO;
import com.quanxiaoha.weblog.common.domain.mapper.TagMapper;
import com.quanxiaoha.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Response addTag(AddTagReqVo addTagReqVo) {
        // VO 转 DO
        List<TagDO> tagDOS = addTagReqVo.getTags()
                .stream().map(tagName -> TagDO.builder()
                        .name(tagName.trim()) // 去掉空格
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        // 批量插入
        try {
            saveBatch(tagDOS);
        } catch (Exception e) {
         log.warn("该标签已经存在", e);
        }
        return Response.success();
    }
}
