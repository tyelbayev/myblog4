package com.example.myblog.controller;

import com.example.myblog.model.Comment;
import com.example.myblog.model.Post;
import com.example.myblog.service.CommentService;
import com.example.myblog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class) // Запускаем тест только для PostController
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private CommentService commentService;

    private Post testPost;
    private List<Comment> testComments;

    @BeforeEach
    void setup() {
        testPost = new Post();
        testPost.setId(1L);
        testPost.setTitle("Test Title");
        testPost.setContent("Test Content");
        testPost.setImgUrl("test.png");
        testPost.setCreatedAt(LocalDateTime.now());
        testPost.setTags("test");

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setPostId(1L);
        comment.setContent("Test Comment");
        comment.setCreatedAt(LocalDateTime.now());
        testComments = Collections.singletonList(comment);
    }

    @Test
    public void testGetPostById() throws Exception {
        when(postService.getPostById(1L)).thenReturn(testPost);
        when(commentService.getCommentsByPostId(1L)).thenReturn(testComments);

        mockMvc.perform(get("/post/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("post", "comments"))
                .andExpect(model().attribute("post", testPost))
                .andExpect(model().attribute("comments", testComments));
    }
}
