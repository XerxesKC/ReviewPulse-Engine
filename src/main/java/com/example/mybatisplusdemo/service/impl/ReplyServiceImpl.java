package com.example.mybatisplusdemo.service.impl;

import com.example.mybatisplusdemo.model.domain.Reply;
import com.example.mybatisplusdemo.mapper.ReplyMapper;
import com.example.mybatisplusdemo.model.domain.User;
import com.example.mybatisplusdemo.service.IReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.bytebuddy.build.RenamingPlugin;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 评论回复表 服务实现类
 * </p>
 *
 * @author zzh
 * @since 2025-07-04
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements IReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<Reply> selectByIdAndContent(String replyId, String replyContent) {
        return replyMapper.selectByIdAndContent(replyId, replyContent);
    }

    @Override
    public List<Reply> getRepliesByCommentIdAndReplyTo(String commentId, String replyTo) {
        return replyMapper.getRepliesByCommentIdAndReplyTo(commentId, replyTo);
    }

    @Override
    public List<Reply> getReplies() {
        List<Reply> list = new ArrayList<>();
        list = replyMapper.getReplies();
        return list;
    }
}
