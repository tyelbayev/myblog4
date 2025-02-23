package com.example.myblog.service;

import com.example.myblog.dao.CommentDao;
import com.example.myblog.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentDao commentDao;

    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentDao.findByPostId(postId);
    }

    public void addComment(Comment comment) {
        commentDao.save(comment);
    }

    public void updateComment(Long id, Comment comment) {
        Comment existingComment = commentDao.findById(id).orElseThrow(() -> new RuntimeException("Комментарий не найден"));
        existingComment.setPostId(comment.getPostId());
        existingComment.setContent(comment.getContent());
        existingComment.setCreatedAt(comment.getCreatedAt());
        commentDao.update(existingComment);
    }

    public void deleteComment(Long id) {
        commentDao.delete(id);
    }

    public List<Comment> getAll() {
        return commentDao.getAll();
    }
}
