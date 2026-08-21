package com.love233niang.weblog.admin.controller;

import com.love233niang.weblog.admin.model.vo.BatchDeleteReqVO;
import com.love233niang.weblog.admin.model.vo.tag.AddTagReqVo;
import com.love233niang.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.love233niang.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.love233niang.weblog.admin.model.vo.tag.SearchTagReqVo;
import com.love233niang.weblog.admin.service.AdminTagService;
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
@RequestMapping("/admin/tag")
@Api(tags = "Admin 标签模块")
public class AdminTagController {
    @Autowired
    private AdminTagService tagService;

    @PostMapping("/add")
    @ApiOperation(value = "添加标签")
    @ApiOperationLog(description = "添加标签")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response addTag(@RequestBody @Validated AddTagReqVo addTagReqVo) {
        return tagService.addTag(addTagReqVo);
    }

    @PostMapping("/list")
    @ApiOperation(value = "标签分页数据获取")
    @ApiOperationLog(description = "标签分页数据获取")
    public PageResponse findTagPageList(@RequestBody @Validated FindTagPageListReqVO findTagPageListReqVO) {
        return tagService.findTagPageList(findTagPageListReqVO);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除标签")
    @ApiOperationLog(description = "删除标签")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteTag(@RequestBody @Validated DeleteTagReqVO deleteTagReqVO) {
        return tagService.deleteTag(deleteTagReqVO);
    }

    @PostMapping("/batch/delete")
    @ApiOperation(value = "批量删除标签")
    @ApiOperationLog(description = "批量删除标签")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response batchDeleteTag(@RequestBody @Validated BatchDeleteReqVO batchDeleteReqVO) {
        return tagService.batchDeleteTag(batchDeleteReqVO);
    }

    @PostMapping("/search")
    @ApiOperation(value = "标签模糊查询")
    @ApiOperationLog(description = "标签模糊查询")
    public Response searchTag(@RequestBody @Validated SearchTagReqVo searchTagReqVo) {
        return tagService.searchTag(searchTagReqVo);
    }

    @PostMapping("/select/list")
    @ApiOperation(value = "查询标签 Select 列表数据")
    @ApiOperationLog(description = "查询标签 Select 列表数据")
    public Response findTagSelectList() {
        return tagService.findTagSelectList();
    }
}
