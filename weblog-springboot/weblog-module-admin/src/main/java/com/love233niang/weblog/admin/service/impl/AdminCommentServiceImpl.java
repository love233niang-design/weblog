package com.love233niang.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.love233niang.weblog.admin.convert.CommentConvert;
import com.love233niang.weblog.admin.model.vo.comment.DeleteCommentReqVO;
import com.love233niang.weblog.admin.model.vo.comment.FindCommentPageListReqVO;
import com.love233niang.weblog.admin.model.vo.comment.FindCommentPageListRspVO;
import com.love233niang.weblog.admin.service.AdminCommentService;
import com.love233niang.weblog.common.domain.dos.CommentDO;
import com.love233niang.weblog.common.domain.mapper.CommentMapper;
import com.love233niang.weblog.common.enums.ResponseCodeEnum;
import com.love233niang.weblog.common.exception.BizException;
import com.love233niang.weblog.common.utils.PageResponse;
import com.love233niang.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminCommentServiceImpl implements AdminCommentService {
    @Autowired
    private CommentMapper commentMapper;

    /**
     * 查询评论分页数据
     *
     * @param findCommentPageListReqVO
     * @return
     */
    @Override
    public Response findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO) {
        Long current = findCommentPageListReqVO.getCurrent();
        Long size = findCommentPageListReqVO.getSize();
        LocalDate startDate = findCommentPageListReqVO.getStartDate();
        LocalDate endDate = findCommentPageListReqVO.getEndDate();
        String routerUrl = findCommentPageListReqVO.getRouterUrl();
        Integer status = findCommentPageListReqVO.getStatus();

        // 执行分页查询
        Page<CommentDO> commentDOPage = commentMapper.selectPageList(current, size, routerUrl, startDate, endDate, status);

        List<CommentDO> commentDOS = commentDOPage.getRecords();

        // DO 转 VO
        List<FindCommentPageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(commentDOS)) {
            vos = commentDOS.stream()
                    .map(commentDO -> CommentConvert.INSTANCE.convertDO2VO(commentDO))
                    .collect(Collectors.toList());
        }
        return PageResponse.success(commentDOPage, vos);
    }


    /**
     * 删除评论
     *
     * @param deleteCommentReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteComment(DeleteCommentReqVO deleteCommentReqVO) {
        Long commentId = deleteCommentReqVO.getId();

        // 查询该评论实际一级评论还是二级评论
        CommentDO commentDO = commentMapper.selectById(commentId);

        if (Objects.isNull(commentDO)) {
            log.warn("该评论不存在, commentId: {}", commentId);
            throw new BizException(ResponseCodeEnum.COMMENT_NOT_FOUND);
        }

        // 删除评论
        commentMapper.deleteById(commentId);

        Long replyCommentId = commentDO.getReplyCommentId();

        // 一级评论
        if (Objects.isNull(replyCommentId)) {
            commentMapper.deleteByParentCommentId(commentId);
        } else {
            // 删除此评论，以及一级评论下的回复
            deleteAllChildComment(commentId);
        }
        return Response.success();
    }


    /**
     * 递归删除所有子评论
     *
     * @param commentId
     */
    private void deleteAllChildComment(Long commentId) {
        List<CommentDO> childCommentDOS = commentMapper.selectByReplyCommentId(commentId);

        if (CollectionUtils.isEmpty(childCommentDOS)) {
            return;
        }

        // 循环递归删除
        childCommentDOS.forEach(childCommentDO -> {
            Long childCommentId = childCommentDO.getId();

            commentMapper.deleteById(commentId);

            // 递归调用
            deleteAllChildComment(childCommentId);
        });
    }
}
