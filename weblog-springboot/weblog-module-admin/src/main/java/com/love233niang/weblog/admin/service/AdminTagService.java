package com.love233niang.weblog.admin.service;


import com.love233niang.weblog.admin.model.vo.BatchDeleteReqVO;
import com.love233niang.weblog.admin.model.vo.tag.AddTagReqVo;
import com.love233niang.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.love233niang.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.love233niang.weblog.admin.model.vo.tag.SearchTagReqVo;
import com.love233niang.weblog.common.utils.PageResponse;
import com.love233niang.weblog.common.utils.Response;

public interface AdminTagService {

    /**
     * 添加标签集合
     * @param addTagReqVo
     * @return
     */
    Response addTag(AddTagReqVo addTagReqVo);

    /**
     * 标签分页列表查询
     * @param findTagPageListReqVO
     * @return
     */
    PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO);

    /**
     * 删除标签
     * @param deleteTagReqVO
     * @return
     */
    Response deleteTag(DeleteTagReqVO deleteTagReqVO);

    /**
     * 批量删除标签
     * @param batchDeleteReqVO
     * @return
     */
    Response batchDeleteTag(BatchDeleteReqVO batchDeleteReqVO);

    /**
     * 根据标签关键词模糊查询
     * @param searchTagReqVo
     * @return
     */
    Response searchTag(SearchTagReqVo searchTagReqVo);


    /**
     * 查询标签 Select 列表数据
     * @return
     */
    Response findTagSelectList();
}
