package com.project.questapp.controllers;

import com.project.questapp.entities.Comment;
import com.project.questapp.requests.CommentCreateRequest;
import com.project.questapp.requests.CommentUpdataRequest;
import com.project.questapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.tokens.CommentToken;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping
    public List<Comment> getAllComments(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId){
        return commentService.getALlCommentsWithParam(userId,postId);
    }
    @GetMapping("/{commentId}")
    public Comment getOneCommet(@PathVariable Long commentId){
        return commentService.getOneCommentById(commentId);
    }
    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest request){
        return commentService.createOneComment(request);
    }
    @PutMapping("/{commentId}")
    public Comment updataOnePost(@PathVariable Long commentId, @RequestBody CommentUpdataRequest updataComment){
        return commentService.updataOneComment(commentId,updataComment);
    }
    @DeleteMapping("/{commentId}")
    public void deleteOnePost(@PathVariable Long commentId){
        commentService.deleteOneComment(commentId);
    }
}
