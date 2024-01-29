package com.springboot.blog.springbootblogrestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//data-> it is annotation used to automatically define getters and setter
//allargscontructor -> In Lombok, the @AllArgsConstructor annotation is used to automatically generate a constructor that includes all non-static, non-transient fields of the class as parameters.
//noargsconstructor -> In Lombok, the @NoArgsConstructor annotation is used to automatically generate a no-args constructor for a class.
@Data
@AllArgsConstructor
@NoArgsConstructor
//used to denote class as table
@Entity

@Table(
        name="posts",uniqueConstraints = {@UniqueConstraint(columnNames={"title"})}
)
public class Post {
    //primary key annotation
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long id;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name= "description", nullable = false)
    private String description;
    @Column(name="content",nullable = false)
    private String content;
}
