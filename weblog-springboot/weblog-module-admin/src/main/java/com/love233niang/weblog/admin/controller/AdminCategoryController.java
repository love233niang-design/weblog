package com.love233niang.weblog.admin.controller;

import com.love233niang.weblog.admin.model.vo.BatchDeleteReqVO;
import com.love233niang.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.love233niang.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.love233niang.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.love233niang.weblog.admin.service.AdminCategoryService;
import com.love233niang.weblog.common.aspect.ApiOperationLog;
import com.love233niang.weblog.common.utils.PageResponse;
import com.love233niang.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 分类模块")
public class AdminCategoryController {
    @Autowired
    private AdminCategoryService categoryService;

    @PostMapping("/category/add")
    @ApiOperation(value = "添加分类")
    @ApiOperationLog(description = "添加分类")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response addCategory(@RequestBody @Validated AddCategoryReqVO addCategoryReqVO) {
        return categoryService.addCategory(addCategoryReqVO);
    }

    @PostMapping("/category/list")
    @ApiOperation(value = "分类分页数据获取")
    @ApiOperationLog(description = "分类分页数据获取")
    public PageResponse findCategoryList(@RequestBody @Validated FindCategoryPageListReqVO findCategoryPageListReqVO) {
        return categoryService.findCategoryPageList(findCategoryPageListReqVO);
    }

    @PostMapping("/category/delete")
    @ApiOperation(value = "删除分类")
    @ApiOperationLog(description = "删除分类")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteCategory(@RequestBody @Validated DeleteCategoryReqVO deleteCategoryReqVO) {
        return categoryService.deleteCategory(deleteCategoryReqVO);
    }

    @PostMapping("/category/batch/delete")
    @ApiOperation(value = "批量删除分类")
    @ApiOperationLog(description = "批量删除分类")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response batchDeleteCategory(@RequestBody @Validated BatchDeleteReqVO batchDeleteReqVO) {
        return categoryService.batchDeleteCategory(batchDeleteReqVO);
    }

    @PostMapping("/category/select/list")
    @ApiOperation(value = "分类 Select 下拉列表数据获取")
    @ApiOperationLog(description = "分类 Select 下拉列表数据获取")
    public Response findCategorySelectList() {
        return categoryService.findCategorySelectList();
    }
}
