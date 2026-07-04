package com.love233niang.weblog.admin.model.vo.article;

import com.love233niang.weblog.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询文章分页数据入参 VO")
public class FindArticlePageListReqVO extends BasePageQuery {
    // 文章标题
    private String title;

    // 发布起始日期
    private LocalDate startTime;

    // 发布结束日期
    private LocalDate endTime;
}
