package com.love233niang.weblog.admin.service;


import com.love233niang.weblog.admin.model.vo.BatchDeleteReqVO;
import com.love233niang.weblog.admin.model.vo.comment.DeleteCommentReqVO;
import com.love233niang.weblog.admin.model.vo.comment.ExamineCommentReqVO;
import com.love233niang.weblog.admin.model.vo.comment.FindCommentPageListReqVO;
import com.love233niang.weblog.common.utils.Response;

public interface AdminCommentService {
    
    /**
     * 查询评论分页数据
     * @param findCommentPageListReqVO
     * @return
     */
    Response findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO);

    /**
     * 删除评论
     * @param deleteCommentReqVO
     * @return
     */
    Response deleteComment(DeleteCommentReqVO deleteCommentReqVO);

    /**
     * 批量删除评论
     * @param batchDeleteReqVO
     * @return
     */
    Response batchDeleteComment(BatchDeleteReqVO batchDeleteReqVO);

    /**
     * 评论审核
     * @param examineCommentReqVO
     * @return
     */
    Response examine(ExamineCommentReqVO examineCommentReqVO);
}

