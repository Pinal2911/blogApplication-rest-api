package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPiException;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import com.springboot.blog.springbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    private PostRepository postRepository;
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper){
        this.commentRepository=commentRepository;
        this.postRepository=postRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment=mapToEntity(commentDto);

        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id","postID"));
        comment.setPost(post);

        Comment newComment=commentRepository.save(comment);



        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postid) {
        List<Comment> comments=commentRepository.findByPostId(postid);

        return  comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id","postId"));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id","commentId"));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPiException(HttpStatus.BAD_REQUEST,"comment does not belong to this post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
        Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post","id","postId"));
        Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment","id","commentId"));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPiException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
        }

        comment.setBody(commentRequest.getBody());
        comment.setEmail(commentRequest.getEmail());
        comment.setName(commentRequest.getName());

        Comment updatedComment=commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id","postId"));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("commnt","id","commentID"));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPiException( HttpStatus.BAD_REQUEST,"comment not found");
        }

        commentRepository.delete(comment);
    }

    //maptoCommentDTO
    private CommentDto mapToDTO(Comment comment){

        CommentDto commentDto= modelMapper.map(comment,CommentDto.class);

        //below is manual process to convert commment to commentDTO
//        commentDto.setId(comment.getId());
//        commentDto.setBody(comment.getBody());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    //map to comment entity (comment dto to comment entity conversion
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment=modelMapper.map(commentDto,Comment.class);
        //below is the manual process to convert commentDTO to commment
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        comment.setId(commentDto.getId());
        return comment;
    }
}
