package com.example.myblog.dao;

import com.example.myblog.model.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentDao {
    private final JdbcTemplate jdbcTemplate;

    public CommentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public final RowMapper<Comment> commentRowMapper = (rs, rowNum) -> {
        Comment comment = new Comment();
        comment.setId(rs.getLong("id"));
        comment.setPostId(rs.getLong("post_id"));
        comment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return comment;
    };

    public List<Comment> findByPostId(Long postId) {
        String sql = "select * from comment where post_id = ?";

        return jdbcTemplate.query(sql, new Object[]{postId}, (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setPostId(rs.getLong("post_id"));
            comment.setContent(rs.getString("content"));
            return comment;
        });
    }

    public void save(Comment comment) {
        String sql = "INSERT INTO comment (post_id, content, created_at) VALUES (?, ?, NOW())";
        jdbcTemplate.update(sql, comment.getPostId(), comment.getContent());
    }

    public void update(Comment comment) {
        String sql = "UPDATE comment SET content = ? WHERE id = ?";
        jdbcTemplate.update(sql, comment.getContent(), comment.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM comment WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<Comment> findById(Long id) {
        String sql = "SELECT * FROM comment WHERE id = ?";
        List<Comment> comments = jdbcTemplate.query(sql, commentRowMapper, id);
        return comments.isEmpty() ? Optional.empty() : Optional.of(comments.get(0));
    }

    public List<Comment> getAll() {
        String sql = "select * from comment";
        List<Comment> comments = jdbcTemplate.query(sql, commentRowMapper);
        return comments;
    }
}
