package com.example.myblog.service;

import com.example.myblog.MyblogApplicationTests;
import com.example.myblog.model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) // Загружаем схему БД перед тестами
public class CommentServiceTest extends MyblogApplicationTests {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("DELETE FROM comment");
        jdbcTemplate.execute("DELETE FROM post");

        jdbcTemplate.update("INSERT INTO post (id, title, content, image_url, created_at, like_count, tags) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)", 1L, "Test Post", "Post Content", "post.png", LocalDateTime.now(), 0, "test");

        jdbcTemplate.update("INSERT INTO comment (post_id, content, created_at) VALUES (?, ?, ?)",
                1L, "Test Comment", LocalDateTime.now());
    }

    @Test
    @Rollback
    public void shouldAddComment() {
        Comment newComment = new Comment();
        newComment.setPostId(1L);
        newComment.setContent("New test comment");
        newComment.setCreatedAt(LocalDateTime.now());

        commentService.addComment(newComment);

        List<Comment> comments = commentService.getCommentsByPostId(1L);
        assertEquals(2, comments.size());
        assertEquals("New test comment", comments.get(1).getContent());
    }

    @Test
    public void shouldReturnCommentsByPostId() {
        List<Comment> comments = commentService.getCommentsByPostId(1L);
        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals("Test Comment", comments.getFirst().getContent());
    }

    @Test
    @Rollback
    public void shouldUpdateComment() {
        Comment updatedComment = new Comment();
        updatedComment.setPostId(1L);
        updatedComment.setContent("Updated Comment");
        updatedComment.setCreatedAt(LocalDateTime.now());

        commentService.updateComment(1L, updatedComment);

        List<Comment> comments = commentService.getCommentsByPostId(1L);
        assertEquals(1, comments.size());
        assertEquals("Updated Comment", comments.getFirst().getContent());
    }

    @Test
    @Rollback
    public void shouldDeleteComment() {
        commentService.deleteComment(1L);

        List<Comment> comments = commentService.getCommentsByPostId(1L);
        assertEquals(0, comments.size());
    }
}
