package com.example.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplusdemo.model.domain.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评论回复表 Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2025-07-04
 */
public interface ReplyMapper extends BaseMapper<Reply> {
    List<Reply> selectByIdAndContent(@Param("replyId") String replyId, @Param("replyContent") String replyContent);

    List<Reply> getReplies();

    List<Reply> getRepliesByCommentIdAndReplyTo(String commentId, String replyTo);
}
