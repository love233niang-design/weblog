package com.quanxiaoha.weblog.admin.controller;

import com.quanxiaoha.weblog.admin.model.vo.tag.AddTagReqVo;
import com.quanxiaoha.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.quanxiaoha.weblog.admin.service.AdminTagService;
import com.quanxiaoha.weblog.common.aspect.ApiOperationLog;
import com.quanxiaoha.weblog.common.utils.PageResponse;
import com.quanxiaoha.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 标签模块")
public class AdminTagController {
    @Autowired
    private AdminTagService tagService;

    @PostMapping("/tag/add")
    @ApiOperation(value = "添加标签")
    @ApiOperationLog(description = "添加标签")
    public Response addTag(@RequestBody @Validated AddTagReqVo addTagReqVo) {
        return tagService.addTag(addTagReqVo);
    }

    @PostMapping("/tag/list")
    @ApiOperation(value = "分类分页数据获取")
    @ApiOperationLog(description = "分类分页数据获取")
    public PageResponse findTagPageList(@RequestBody @Validated FindTagPageListReqVO findTagPageListReqVO) {
        return tagService.findTagPageList(findTagPageListReqVO);
    }
//
//    @PostMapping("/category/delete")
//    @ApiOperation(value = "删除分类")
//    @ApiOperationLog(description = "删除分类")
//    public Response deleteCategory(@RequestBody @Validated DeleteCategoryReqVO deleteCategoryReqVO) {
//        return categoryService.deleteCategory(deleteCategoryReqVO);
//    }
//
//    @PostMapping("/category/select/list")
//    @ApiOperation(value = "分类 Select 下拉列表数据获取")
//    @ApiOperationLog(description = "分类 Select 下拉列表数据获取")
//    public Response findCategorySelectList() {
//        return categoryService.findCategorySelectList();
//    }
}