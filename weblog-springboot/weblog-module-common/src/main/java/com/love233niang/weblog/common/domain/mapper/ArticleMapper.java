package com.love233niang.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.love233niang.weblog.common.domain.dos.ArticleDO;

import java.time.LocalDate;
import java.util.Objects;

public interface ArticleMapper extends BaseMapper<ArticleDO> {
    /**
     * 分页查询
     * @param current 当前页码
     * @param size 每页展示的数据量
     * @param title 文章标题
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    default Page<ArticleDO> selectPageList(Long current, Long size, String title, LocalDate startDate, LocalDate endDate) {
        Page<ArticleDO> page = new Page<>(current, size);

        LambdaQueryWrapper<ArticleDO> wrapper = Wrappers.<ArticleDO>lambdaQuery()
                .like(StringUtils.isNotBlank(title), ArticleDO::getTitle, title)
                .ge(Objects.nonNull(startDate), ArticleDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), ArticleDO::getCreateTime, endDate)
                .orderByDesc(ArticleDO::getCreateTime);
        return selectPage(page, wrapper);
    }
}
