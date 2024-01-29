package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.springframework.stereotype.Service;

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
    public List<PostDto> getAllPosts() {
        List<Post> posts=postRepository.findAll();
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

    }

    @Override
    public PostDto getPostById(long id) {
        Post p=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id","id"));
        PostDto result=mapToDTO(p);
        return result;
    }

    private PostDto mapToDTO(Post post){
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setDescription(postDto.getDescription());
        postDto.setContent(postDto.getContent());
        postDto.setTitle(postDto.getTitle());
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
