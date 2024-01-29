package com.springboot.blog.springbootblogrestapi.payload;

import lombok.Data;

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
    private String title;
    private String description;
    private String content;
}
