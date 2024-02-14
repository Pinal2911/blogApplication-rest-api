package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.aspectj.SpringConfiguredConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    private CategoryRepository categoryRepository;
    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper,CategoryRepository categoryRepository){
        this.postRepository=postRepository;
        this.modelMapper=modelMapper;
        this.categoryRepository=categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category=categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("category","id","categoryid"));
        //convert DTO to entity
        Post p=mapToEntity(postDto);
        Post newPost=postRepository.save(p);
        newPost.setCategory(category);

        //convert entity to DTO

        PostDto postResponse=mapToDTO(p);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize, String sortBy,String sortDir) {


        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable=PageRequest.of(pageNo,pageSize, sort);
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
        Category category=categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("category","id","categoryid"));
        p.setCategory(category);
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

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {

        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","id","categoryId"));

        List<Post> posts=postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post)->mapToDTO(post)).collect(Collectors.toList());

    }

    private PostDto mapToDTO(Post post){
        //this is direct method via model mapper to convert from post to postdto
        PostDto postDto=modelMapper.map(post,PostDto.class);
        //below is themanual process to convert post to postdto
//        postDto.setId(post.getId());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
//        postDto.setTitle(post.getTitle());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        //thisis direct method to convert from postdto to post
        //source - > postdto
        //dest -> post


        Post post=modelMapper.map(postDto,Post.class);

        //below is manual process to convert postdto to post
//        post.setContent(postDto.getContent());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
        return post;
    }
}
