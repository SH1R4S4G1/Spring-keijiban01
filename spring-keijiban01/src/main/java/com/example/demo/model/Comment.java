package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
    private Long id;
    
    public Comment () {
        List<Comment> replys = new ArrayList<Comment>();
        this.setReplys(replys);
    }

    @NotBlank
    @Size(max = 40)
    private String content;
    
//    @ManyToOne
//    private SiteUser siteUser;
    private String username;
    
    private LocalDateTime postDateTime;
    
    private Long parentCommentId;
    
    //一時的な変数
    private List<Comment> replys;
    
}
