package com.example.myblog.controller;

import com.example.myblog.model.Comment;
import com.example.myblog.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAll() {
        return commentService.getAll();
    }

    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return ResponseEntity.ok("Комментарий добавлен");
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable("id") Long id, @RequestBody Comment comment) {
        commentService.updateComment(id, comment);
        return ResponseEntity.ok("Комментарий обновлен");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Комментарий удален");
    }

}
