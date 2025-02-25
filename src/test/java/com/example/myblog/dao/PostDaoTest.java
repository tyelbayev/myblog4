package com.example.myblog.dao;

import com.example.myblog.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Transactional
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PostDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private PostDao postDao;

    @BeforeEach
    void setUp() {
        postDao = new PostDao(jdbcTemplate);

        jdbcTemplate.execute("DELETE FROM post"); // Очищаем таблицу перед тестами

        jdbcTemplate.update("INSERT INTO post (title, content, image_url, created_at, like_count, tags) " +
                "VALUES (?, ?, ?, ?, ?, ?)", "Test Title", "Test Content", "test.png", LocalDateTime.now(), 0, "test");
    }

    @Test
    void shouldSavePost() {
        Post newPost = new Post();
        newPost.setTitle("New Post");
        newPost.setContent("New Content");
        newPost.setImgUrl("new.png");
        newPost.setTags("java");

        postDao.save(newPost);

        List<Post> posts = postDao.findAll(0, 10);
        assertThat(posts).hasSize(2);
        assertThat(posts.get(1).getTitle()).isEqualTo("New Post");
    }

    @Test
    void shouldFindPostById() {
        Optional<Post> post = postDao.findById(1L);

        assertThat(post).isPresent();
        assertThat(post.get().getTitle()).isEqualTo("Test Title");
    }

    @Test
    void shouldReturnEmptyIfPostNotFound() {
        Optional<Post> post = postDao.findById(999L);

        assertThat(post).isEmpty();
    }

    @Test
    void shouldFindAllPosts() {
        List<Post> posts = postDao.findAll(0, 10);

        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("Test Title");
    }

    @Test
    void shouldFindAllByTag() {
        List<Post> posts = postDao.findAllByTag("test");

        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTags()).contains("test");
    }

    @Test
    void shouldUpdatePost() {
        Post updatedPost = new Post();
        updatedPost.setId(1L);
        updatedPost.setTitle("Updated Title");
        updatedPost.setContent("Updated Content");
        updatedPost.setImgUrl("updated.png");
        updatedPost.setTags("updated");

        postDao.update(updatedPost);

        Optional<Post> postAfterUpdate = postDao.findById(1L);
        assertThat(postAfterUpdate).isPresent();
        assertThat(postAfterUpdate.get().getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void shouldIncreaseLikeCount() {
        postDao.increaseLikeCount(1L);

        Optional<Post> post = postDao.findById(1L);
        assertThat(post).isPresent();
        assertThat(post.get().getLikeCount()).isEqualTo(1);
    }

    @Test
    void shouldDeletePost() {
        postDao.delete(1L);

        Optional<Post> post = postDao.findById(1L);
        assertThat(post).isEmpty();
    }
}
