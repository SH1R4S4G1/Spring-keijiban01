package com.example.demo.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.demo.validator.UniqueLogin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUser {
    private Long id;

    @Size(min = 2, max = 20)
    @UniqueLogin
    private String username;

    @Size(min = 4, max = 255)
    private String password;

    @NotBlank
    @Email
    private String email;

//    private int gender;
    private boolean admin;
    private String role;
    private boolean active = true;
    
//    @OneToMany(mappedBy="siteUser")
//    private List<Comment> comments;
        
}
