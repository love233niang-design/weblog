package com.love233niang.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.love233niang.weblog.admin.model.vo.dashboard.FindDashboardStatisticsInfoRspVO;
import com.love233niang.weblog.admin.service.AdminDashboardService;
import com.love233niang.weblog.common.domain.dos.ArticleDO;
import com.love233niang.weblog.common.domain.mapper.ArticleMapper;
import com.love233niang.weblog.common.domain.mapper.CategoryMapper;
import com.love233niang.weblog.common.domain.mapper.TagMapper;
import com.love233niang.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdminDashboardServiceImpl implements AdminDashboardService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Response findDashboardStatistics() {
        // 查询文章总数
        Long articleTotalConut = articleMapper.selectCount(Wrappers.emptyWrapper());
        // 查询分类总数
        Long categoryTotalCount = categoryMapper.selectCount(Wrappers.emptyWrapper());
        // 查询标签总数
        Long tagTotalCount = tagMapper.selectCount(Wrappers.emptyWrapper());

        // 查询总浏览量
        List<ArticleDO> articleDOS = articleMapper.selectAllReadNum();
        Long pvTotalCount = 0L;

        if (!CollectionUtils.isEmpty(articleDOS)) {
            pvTotalCount = articleDOS.stream().mapToLong(ArticleDO::getReadNum).sum();
        }
        FindDashboardStatisticsInfoRspVO vo = FindDashboardStatisticsInfoRspVO.builder()
                .articleTotalCount(articleTotalConut)
                .categoryTotalCount(categoryTotalCount)
                .tagTotalCount(tagTotalCount)
                .pvTotalCount(pvTotalCount)
                .build();

        return Response.success(vo);
    }
}
