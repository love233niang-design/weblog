package com.love233niang.weblog.admin.convert;

import com.love233niang.weblog.admin.model.vo.article.FindArticleDetailRspVO;
import com.love233niang.weblog.common.domain.dos.ArticleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleDetailConvert {
    ArticleDetailConvert INSTANCE = Mappers.getMapper(ArticleDetailConvert.class);

    FindArticleDetailRspVO convertDO2VO(ArticleDO bean);
}
