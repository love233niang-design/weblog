package com.quanxiaoha.weblog.admin.convert;

import com.quanxiaoha.weblog.admin.model.vo.article.FindArticleDetailRspVO;
import com.quanxiaoha.weblog.common.domain.dos.ArticleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleDetailConvert {
    ArticleDetailConvert INSTANCE = Mappers.getMapper(ArticleDetailConvert.class);

    FindArticleDetailRspVO convertDO2VO(ArticleDO bean);
}
