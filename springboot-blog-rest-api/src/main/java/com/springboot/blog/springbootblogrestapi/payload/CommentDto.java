package com.springboot.blog.springbootblogrestapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    @NotEmpty
    @Size(min=5,message = "name must of atleast 5 characters")
    private String name;
    @NotEmpty
    @Email(message = "enter valid mail")
    private String email;
    @NotEmpty
    @Size(min=10, message = "body must be atleast 10 characters")
    private String body;
}
