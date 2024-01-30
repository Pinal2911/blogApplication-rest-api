package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    public PostServiceImpl(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert DTO to entity
        Post p=mapToEntity(postDto);
        Post newPost=postRepository.save(p);

        //convert entity to DTO

        PostDto postResponse=mapToDTO(p);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize) {

        Pageable pageable=PageRequest.of(pageNo,pageSize);
        Page<Post> posts=postRepository.findAll(pageable);

        List<Post> listOfPosts=posts.getContent();
        List<PostDto> content= listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements((int) posts.getTotalElements());
        postResponse.setLast(posts.isLast());


        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post p=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id","id"));
        PostDto result=mapToDTO(p);
        System.out.println(result);
        return mapToDTO(p);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post p=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id","id"));
        p.setTitle(postDto.getTitle());
        p.setDescription(postDto.getDescription());
        p.setContent(postDto.getContent());
        Post updatedPost=postRepository.save(p);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deleteById(long id) {
        Post p=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id","id"));
        postRepository.delete(p);
    }

    private PostDto mapToDTO(Post post){
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        postDto.setTitle(post.getTitle());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post=new Post();
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        return post;
    }
}
