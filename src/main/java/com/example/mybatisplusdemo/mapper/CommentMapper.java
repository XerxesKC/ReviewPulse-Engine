package com.example.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.model.domain.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户评论表 Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2025-07-04
 */
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> selectByIdAndContent(@Param("commentId") String commentId, @Param("commentContent") String commentContent);

    List<Comment> getMerchantPostComments(@Param("merchantId") String merchantId);

    List<Comment> getMerchantComments(@Param("merchantId") String merchantId);

    IPage<Comment> selectByMerchantIdWithPage(@Param("merchantId") String merchantId, Page<Comment> page);
    IPage<Comment> selectByMerchantIdAndPostId(@Param("merchantId") String merchantId,@Param("postId") String postId, Page<Comment> page);
    List<Comment> getComments();

    //List<Comment> getCommentsByUserId(@Param("userId") String userId);

    Integer updateStatus(@Param("comment") Comment comment);

    Integer deleteMy(@Param("comment") Comment comment);
}
