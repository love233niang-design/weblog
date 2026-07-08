package com.love233niang.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.love233niang.weblog.admin.model.vo.dashboard.FindDashboardPVStatisticsInfoRspVO;
import com.love233niang.weblog.admin.model.vo.dashboard.FindDashboardStatisticsInfoRspVO;
import com.love233niang.weblog.admin.service.AdminDashboardService;
import com.love233niang.weblog.common.constants.Constants;
import com.love233niang.weblog.common.domain.dos.ArticleDO;
import com.love233niang.weblog.common.domain.dos.ArticlePublishCountDO;
import com.love233niang.weblog.common.domain.dos.StatisticsArticlePVDO;
import com.love233niang.weblog.common.domain.mapper.ArticleMapper;
import com.love233niang.weblog.common.domain.mapper.CategoryMapper;
import com.love233niang.weblog.common.domain.mapper.StatisticsArticlePVMapper;
import com.love233niang.weblog.common.domain.mapper.TagMapper;
import com.love233niang.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminDashboardServiceImpl implements AdminDashboardService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private StatisticsArticlePVMapper articlePVMapper;

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

    /**
     * 获取文章发布热点统计信息
     *
     * @return
     */
    @Override
    public Response findDashboardPublishArticleStatistics() {
        // 当前日期
        LocalDate currDate = LocalDate.now();

        // 当前日期倒退一年
        LocalDate startDate = currDate.minusYears(1);

        // 查找这一年内，每日发布的文章数量
        List<ArticlePublishCountDO> articlePublishCountDOS = articleMapper.selectDateArticlePublishCount(startDate, currDate.plusDays(1));
        Map<LocalDate, Long> map = null;
        if (!CollectionUtils.isEmpty(articlePublishCountDOS)) {
            // DO 转 map
            Map<LocalDate, Long> dateArticleCountMap = articlePublishCountDOS.stream()
                    .collect(Collectors.toMap(ArticlePublishCountDO::getDate, ArticlePublishCountDO::getCount));
            // 有序 map, 放回的日期文章数以升序排列
            map = Maps.newLinkedHashMap();
            // 从上一年的今天循环到今天
            for (; startDate.isBefore(currDate) || startDate.isEqual(currDate); startDate = startDate.plusDays(1)) {
                // 以日期作为 key 从 dateArticleCountMap 中获取文章发布总量
                Long count = dateArticleCountMap.get(startDate);
                map.put(startDate, Objects.isNull(count) ? 0 : count);
            }
        }
        return Response.success(map);
    }

    /**
     * 获取文章最近一周 PV 访问量统计信息
     *
     * @return
     */
    @Override
    public Response findDashboardPVStatistics() {
        // 查询最近一周的 PV 访问记录
        List<StatisticsArticlePVDO> articlePVDOS = articlePVMapper.selectLatestWeekRecords();
        Map<LocalDate, Long> pvDateCountMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(articlePVDOS)) {
            // 转 Map， 方便后续通过日期获取 PV 访问量
            pvDateCountMap = articlePVDOS.stream()
                    .collect(Collectors.toMap(StatisticsArticlePVDO::getPvDate, StatisticsArticlePVDO::getPvCount));
        }
        FindDashboardPVStatisticsInfoRspVO vo = null;
        // 日期集合
        List<String> pvDates = Lists.newArrayList();
        // PV 访问量集合
        List<Long> pvCounts = Lists.newArrayList();

        // 当前日期
        LocalDate currDate = LocalDate.now();
        // 一周前
        LocalDate tmpDate = currDate.minusWeeks(1);
        for (; tmpDate.isBefore(currDate) || tmpDate.equals(currDate); tmpDate = tmpDate.plusDays(1)) {
            pvDates.add(tmpDate.format(Constants.MONTH_DAY_FORMATTER));
            Long pvCount = pvDateCountMap.get(tmpDate);
            pvCounts.add(Objects.isNull(pvCount) ? 0 : pvCount);
        }
        vo = FindDashboardPVStatisticsInfoRspVO.builder()
                .pvDates(pvDates)
                .pvCounts(pvCounts)
                .build();

        return Response.success(vo);
    }
}
