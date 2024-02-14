package com.springboot.blog.springbootblogrestapi.payload;

import com.springboot.blog.springbootblogrestapi.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

//there are two ways to send data
//1st-> tranfer jparepository as a data between client and server
//2nd -> transfer dto as a data between client and server (Data transfer object)
//Data Transfer Object Design Pattern is a frequently used
//design pattern. It is basically used to pass data with multiple
//attributes in one shot from client to server, to avoid multiple
//calls to a remote server
@Data
public class PostDto {

    private long id;
    @NotEmpty
    @Size(min=2, message = "post title should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min=10, message = "post description should have at least 10 characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    private Long categoryId;
}
