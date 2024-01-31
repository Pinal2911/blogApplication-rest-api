package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import com.springboot.blog.springbootblogrestapi.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    public PostController(PostService postService){
        this.postService=postService;
    }
    //create a new post rest api
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all posts rest api
    //simple url where default values will be considered : http://localhost:8080/api/posts
    //url with pagination : http://localhost:8080/api/posts?pageNo=0&pageSize=10
    //url with pagination and sorting : http://localhost:8080/api/posts?pageNo=0&pageSize=10&sortBy=id
    //url with pagination and sorting with sorting direction ascending or descending : http://localhost:8080/api/posts?pageNo=0&pageSize=10&sortBy=content&sortDir=desc
   @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue =AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue=AppConstants.DEFAULT_SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir", defaultValue =AppConstants.DEFAULT_SORT_DIRECTION,required = false)String sortDir
   ){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    //get post by id rest api
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id")long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //udpate the post by id rest api
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable(name="id") long id){
        PostDto postResponse=postService.updatePost(postDto,id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //delete the post by id rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id")long id){
        postService.deleteById(id);
        return new ResponseEntity("post deleted successfully",HttpStatus.OK);
    }
}
