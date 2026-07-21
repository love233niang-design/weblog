package com.love233niang.weblog.service;


import com.love233niang.weblog.common.utils.Response;
import com.love233niang.weblog.model.vo.comment.FindQQUserInfoReqVO;
import com.love233niang.weblog.model.vo.comment.PublishCommentReqVO;

/**
 * @author: 犬小哈
 * @url: www.quanxiaoha.com
 * @date: 2023-09-15 14:03
 * @description: 评论
 **/
public interface CommentService {

    /**
     * 根据 QQ 号获取用户信息
     * @param findQQUserInfoReqVO
     * @return
     */
    Response findQQUserInfo(FindQQUserInfoReqVO findQQUserInfoReqVO);


    /**
     * 发布评论
     * @param publishCommentReqVO
     * @return
     */
    Response publishComment(PublishCommentReqVO publishCommentReqVO);
}

