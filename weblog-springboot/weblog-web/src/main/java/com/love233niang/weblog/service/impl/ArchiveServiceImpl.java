package com.love233niang.weblog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.love233niang.weblog.common.domain.dos.ArticleDO;
import com.love233niang.weblog.common.domain.mapper.ArticleMapper;
import com.love233niang.weblog.common.utils.PageResponse;
import com.love233niang.weblog.common.utils.Response;
import com.love233niang.weblog.convert.ArticleConvert;
import com.love233niang.weblog.model.vo.archive.FindArchiveArticlePageListReqVO;
import com.love233niang.weblog.model.vo.archive.FindArchiveArticlePageListRspVO;
import com.love233niang.weblog.model.vo.archive.FindArchiveArticleRspVO;
import com.love233niang.weblog.service.ArchiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArchiveServiceImpl implements ArchiveService {
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取文章归档分页数据
     *
     * @param findArchiveArticlePageListReqVO
     * @return
     */
    @Override
    public Response findArchivePageList(FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO) {
        Long current = findArchiveArticlePageListReqVO.getCurrent();
        Long size = findArchiveArticlePageListReqVO.getSize();
        Page<ArticleDO> articleDOPage = articleMapper.selectPageList(current, size, null, null, null);
        List<ArticleDO> articleDOS = articleDOPage.getRecords();

        List<FindArchiveArticlePageListRspVO> vos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(articleDOS)) {
            // DO 转 VO
            List<FindArchiveArticleRspVO> archiveArticleRspVOS = articleDOS.stream()
                    .map(articleDO -> ArticleConvert.INSTANCE.convertDO2ArchiveArticleVO(articleDO))
                    .collect(Collectors.toList());

            // 按创建的月份进行分组
            Map<YearMonth, List<FindArchiveArticleRspVO>> map = archiveArticleRspVOS.stream().collect(Collectors.groupingBy(FindArchiveArticleRspVO::getCreateMonth));
            // 使用 TreeMap 按月份倒序排列
            Map<YearMonth, List<FindArchiveArticleRspVO>> sortedMap = new TreeMap<>(Collections.reverseOrder());
            sortedMap.putAll(map);

            sortedMap.forEach((k, v) -> vos.add(FindArchiveArticlePageListRspVO
                    .builder()
                    .month(k)
                    .articles(v).build()));
        }
        return PageResponse.success(articleDOPage, vos);
    }
}
