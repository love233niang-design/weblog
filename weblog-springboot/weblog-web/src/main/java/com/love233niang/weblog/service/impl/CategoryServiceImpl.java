package com.love233niang.weblog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.love233niang.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.love233niang.weblog.common.domain.dos.ArticleDO;
import com.love233niang.weblog.common.domain.dos.CategoryDO;
import com.love233niang.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.love233niang.weblog.common.domain.mapper.ArticleMapper;
import com.love233niang.weblog.common.domain.mapper.CategoryMapper;
import com.love233niang.weblog.common.enums.ResponseCodeEnum;
import com.love233niang.weblog.common.exception.BizException;
import com.love233niang.weblog.common.utils.PageResponse;
import com.love233niang.weblog.common.utils.Response;
import com.love233niang.weblog.convert.ArticleConvert;
import com.love233niang.weblog.model.vo.category.FindCategoryArticlePageListReqVO;
import com.love233niang.weblog.model.vo.category.FindCategoryArticlePageListRspVO;
import com.love233niang.weblog.model.vo.category.FindCategoryListReqVO;
import com.love233niang.weblog.model.vo.category.FindCategoryListRspVO;
import com.love233niang.weblog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取分类列表
     *
     * @return
     */
    @Override
    public Response findCategoryList(FindCategoryListReqVO findCategoryListReqVO) {
        Long size = findCategoryListReqVO.getSize();
        // 查询分类列表
        List<CategoryDO> categoryDOS = null;
        if (Objects.isNull(size) || size == 0) {
            // 查询所有分类
            categoryDOS = categoryMapper.selectList(null);
        } else {
            categoryDOS = categoryMapper.selectByLimit(size);
        }
        // DO 转 VO
        List<FindCategoryListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(categoryDOS)) {
            vos = categoryDOS.stream()
                    .map(categoryDO -> FindCategoryListRspVO.builder()
                            .id(categoryDO.getId())
                            .name(categoryDO.getName())
                            .articlesTotal(categoryDO.getArticlesTotal())
                            .build())
                    .collect(Collectors.toList());
        }
        return Response.success(vos);
    }

    /**
     * 获取分类文章分页列表
     *
     * @param findCategoryArticlePageListReqVO
     * @return
     */
    @Override
    public Response findCategoryArticlePageList(FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO) {
        Long current = findCategoryArticlePageListReqVO.getCurrent();
        Long size = findCategoryArticlePageListReqVO.getSize();
        Long categoryId = findCategoryArticlePageListReqVO.getId();

        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("==> 该分类不存在, categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }
        List<ArticleCategoryRelDO> articleCategoryRelDOS = articleCategoryRelMapper.selectByCategoryId(categoryId);
        if (CollectionUtils.isEmpty(articleCategoryRelDOS)) {
            log.info("==> 该分类下还未发布任何文章, categoryId: {}", categoryId);
            return PageResponse.success(null, null);
        }
        List<Long> articleIds = articleCategoryRelDOS.stream().map(ArticleCategoryRelDO::getArticleId).collect(Collectors.toList());
        Page<ArticleDO> page = articleMapper.selectPageListByArticleIds(current, size, articleIds);
        List<ArticleDO> articleDOS = page.getRecords();

        List<FindCategoryArticlePageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(articleDOS)) {
            vos = articleDOS.stream()
                    .map(articleDO -> ArticleConvert.INSTANCE.convertDO2CategoryArticleVO(articleDO))
                    .collect(Collectors.toList());
        }
        return PageResponse.success(page, vos);
    }
}
