package com.example.myblog.controller;

import com.example.myblog.model.Comment;
import com.example.myblog.model.Post;
import com.example.myblog.service.CommentService;
import com.example.myblog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        List<Comment> comments = commentService.getCommentsByPostId(id);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);

        return "post";
    }

    @PostMapping
    public String addPost(@ModelAttribute Post post) {
        postService.addPost(post);
        return "redirect:/";
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable("id") Long id) {
        postService.likePost(id);
        return ResponseEntity.ok("Лайк поставлен");
    }

    @GetMapping("/filter")
    public List<Post> getPostsByTag(@RequestParam("tag") String tag) {
        return postService.getPostsByTag(tag);
    }

    @PostMapping("/edit")
    public String updatePost(@RequestParam("id") Long id, @ModelAttribute Post post) {
        postService.updatePost(id, post);
        return "redirect:/post/" + id;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Пост удален");
    }

}
