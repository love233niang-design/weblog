package com.quanxiaoha.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quanxiaoha.weblog.admin.model.vo.tag.*;
import com.quanxiaoha.weblog.admin.service.AdminTagService;
import com.quanxiaoha.weblog.common.domain.dos.TagDO;
import com.quanxiaoha.weblog.common.domain.mapper.TagMapper;
import com.quanxiaoha.weblog.common.enums.ResponseCodeEnum;
import com.quanxiaoha.weblog.common.model.vo.SelectRspVO;
import com.quanxiaoha.weblog.common.utils.PageResponse;
import com.quanxiaoha.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {
    @Autowired
    private TagMapper tagMapper;

    /**
     * 添加标签集合
     *
     * @param addTagReqVo
     * @return
     */
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

    /**
     * 标签分页列表查询
     *
     * @param findTagPageListReqVO
     * @return
     */
    @Override
    public PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO) {
        // 分页参数
        Long current = findTagPageListReqVO.getCurrent();
        Long size = findTagPageListReqVO.getSize();
        String name = findTagPageListReqVO.getName();
        LocalDate startDate = findTagPageListReqVO.getStartDate();
        LocalDate endDate = findTagPageListReqVO.getEndDate();

        Page<TagDO> page = tagMapper.selectPageList(current, size, name, startDate, endDate);
        List<TagDO> records = page.getRecords();

        //DO 转 VO
        List<FindTagPageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(records)) {
            vos = records.stream().map(tagDO -> FindTagPageListRspVO.builder()
                    .id(tagDO.getId())
                    .name(tagDO.getName())
                    .createTime(tagDO.getCreateTime())
                    .build()).collect(Collectors.toList());
        }
        return PageResponse.success(page, vos);
    }

    /**
     * 删除标签
     *
     * @param deleteTagReqVO
     * @return
     */
    @Override
    public Response deleteTag(DeleteTagReqVO deleteTagReqVO) {
        Long tagId = deleteTagReqVO.getId();
        int count = tagMapper.deleteById(tagId);

        return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.TAG_NOT_EXISTED);
    }

    /**
     * 根据标签关键词模糊查询
     *
     * @param searchTagReqVo
     * @return
     */
    @Override
    public Response searchTag(SearchTagReqVo searchTagReqVo) {
        String key = searchTagReqVo.getKey();
        // 执行模糊查询
        List<TagDO> tagDOS = tagMapper.selectByKey(key);

        // do 转 vo
        List<SelectRspVO> vos = null;
        if (!CollectionUtils.isEmpty(tagDOS)) {
            vos = tagDOS.stream().map(tagDO -> SelectRspVO.builder()
                    .label(tagDO.getName())
                    .value(tagDO.getId())
                    .build()).collect(Collectors.toList());
        }
        return Response.success(vos);
    }
}
