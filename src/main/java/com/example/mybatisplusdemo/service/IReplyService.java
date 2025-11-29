package com.example.mybatisplusdemo.service;

import com.example.mybatisplusdemo.model.domain.Reply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 评论回复表 服务类
 * </p>
 *
 * @author zzh
 * @since 2025-07-04
 */
public interface IReplyService extends IService<Reply> {
    List<Reply> selectByIdAndContent(String replyId, String replyContent);

    /**
     * 查询针对某条评论和用户的回复
     */
    List<Reply> getRepliesByCommentIdAndReplyTo(String commentId, String replyTo);

    List<Reply> getReplies();
}
