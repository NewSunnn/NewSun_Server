package com.newsun.Newsun.controller;

import com.newsun.Newsun.dto.comment.request.CreateCommentDto;
import com.newsun.Newsun.dto.comment.response.CommentDto;
import com.newsun.Newsun.dto.comment.response.CommentListDto;
import com.newsun.Newsun.dto.exception.ResponseDto;
import com.newsun.Newsun.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/post/{postId}/comment")
    public ResponseDto<CommentListDto> listComment(@PathVariable Long postId) {
        return ResponseDto.ok(commentService.listComment(postId));
    }

    @PostMapping("/post/{postId}/comment")
    public ResponseDto<Boolean> createComment(
            @PathVariable Long postId,
            @RequestBody CreateCommentDto createCommentDto
    ) {
        return ResponseDto.created(commentService.createComment(postId, createCommentDto));
    }

    @PatchMapping("/comment/{commentId}")
    public ResponseDto<CommentDto> patchComment(
            @PathVariable Long commentId,
            @RequestBody CreateCommentDto patchCommentDto
    ) {
        return ResponseDto.ok(commentService.patchComment(commentId, patchCommentDto));
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseDto<Boolean> deleteComment(@PathVariable Long commentId) {
        return ResponseDto.ok(commentService.deleteComment(commentId));
    }
}
