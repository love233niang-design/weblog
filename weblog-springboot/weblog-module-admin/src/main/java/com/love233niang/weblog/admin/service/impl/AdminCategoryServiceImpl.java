package com.love233niang.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.love233niang.weblog.admin.model.vo.BatchDeleteReqVO;
import com.love233niang.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.love233niang.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.love233niang.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.love233niang.weblog.admin.model.vo.category.FindCategoryPageListRspVO;
import com.love233niang.weblog.admin.service.AdminCategoryService;
import com.love233niang.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.love233niang.weblog.common.domain.dos.CategoryDO;
import com.love233niang.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.love233niang.weblog.common.domain.mapper.CategoryMapper;
import com.love233niang.weblog.common.enums.ResponseCodeEnum;
import com.love233niang.weblog.common.exception.BizException;
import com.love233niang.weblog.common.model.vo.SelectRspVO;
import com.love233niang.weblog.common.utils.PageResponse;
import com.love233niang.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;

    /**
     * 添加分类
     *
     * @param addCategoryReqVO
     * @return
     */
    @Override
    public Response addCategory(AddCategoryReqVO addCategoryReqVO) {
        String categoryName = addCategoryReqVO.getName();

        // 判断分类名称是否已存在
        CategoryDO categoryDO = categoryMapper.selectByName(categoryName);
        if (Objects.nonNull(categoryDO)) {
            log.warn("分类名称： {}, 此分类已存在", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);
        }

        // 构建 DO 类
        CategoryDO insertCategoryDO = CategoryDO.builder()
                .name(addCategoryReqVO.getName().trim()) // .trim() 作用，它去掉了字符串首尾的空白字符（空格、制表符等）
                .build();

        categoryMapper.insert(insertCategoryDO);
        return Response.success();
    }

    /**
     * 分类分页
     *
     * @param findCategoryPageListReqVO
     * @return
     */
    @Override
    public PageResponse findCategoryPageList(FindCategoryPageListReqVO findCategoryPageListReqVO) {
        // 获取当前页、一级每页需要展示的数据量
        Long current = findCategoryPageListReqVO.getCurrent();
        Long size = findCategoryPageListReqVO.getSize();
        String name = findCategoryPageListReqVO.getName();
        LocalDate startDate = findCategoryPageListReqVO.getStartDate();
        LocalDate endDate = findCategoryPageListReqVO.getEndDate();

        // 执行分页查询
        Page<CategoryDO> categoryDOPage = categoryMapper.selectPageList(current, size, name, startDate, endDate);

        List<CategoryDO> categoryDOS = categoryDOPage.getRecords();

        // DO 转 VO
        List<FindCategoryPageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(categoryDOS)) {
            vos = categoryDOS.stream()
                    .map(categoryDO -> FindCategoryPageListRspVO.builder()
                            .id(categoryDO.getId())
                            .name(categoryDO.getName())
                            .createTime(categoryDO.getCreateTime())
                            .articlesTotal(categoryDO.getArticlesTotal())
                            .build())
                    .collect(Collectors.toList());
        }
        return PageResponse.success(categoryDOPage, vos);
    }

    /**
     * 删除分类
     *
     * @param deleteCategoryReqVO
     * @return
     */
    @Override
    public Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO) {
        deleteCategoryById(deleteCategoryReqVO.getId());
        return Response.success();
    }

    /**
     * 批量删除分类
     *
     * @param batchDeleteReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response batchDeleteCategory(BatchDeleteReqVO batchDeleteReqVO) {
        batchDeleteReqVO.getIds().stream()
                .distinct()
                .forEach(this::deleteCategoryById);

        return Response.success();
    }

    private void deleteCategoryById(Long categoryId) {
        ArticleCategoryRelDO articleCategoryRelDO = articleCategoryRelMapper.selectOneByCategoryId(categoryId);
        if (Objects.nonNull(articleCategoryRelDO)) {
            log.warn("==> 此分类下包含文章，无法删除，categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_CAN_NOT_DELETE);
        }

        // 删除分类
        categoryMapper.deleteById(categoryId);
    }

    /**
     * 查询分类下拉列表
     *
     * @return
     */
    @Override
    public Response findCategorySelectList() {
        List<CategoryDO> categoryDOS = categoryMapper.selectList(null);

        // DO 转 VO
        List<SelectRspVO> selectRspVOS = null;
        if (!CollectionUtils.isEmpty(categoryDOS)) {
            selectRspVOS = categoryDOS.stream()
                    .map(categoryDO -> SelectRspVO.builder()
                            .label(categoryDO.getName())
                            .value(categoryDO.getId())
                            .build())
                    .collect(Collectors.toList());
        }
        return Response.success(selectRspVOS);
    }
}
