package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Comment;
import com.example.demo.model.SiteUser;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserDetailsServiceImpl;
import com.example.demo.util.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SecurityController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final CommentService commentService;
    private final UserDetailsServiceImpl siteUserService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String getAllComments(Authentication loginUser, @ModelAttribute Comment comment, Model model) {
//        model.addAttribute("comments", commentService.findAll());
//		currentUserはPOSTMAPPINGにもあるのでできれば処理を統一したい
    	String currentUser = loginUser.getName();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("commentwithreplys", commentService.findAllCommentWithreplys());
        
        return "commentlist";
    }
    
    //Bindingresultは、commentのエラーを拾いたいならcommentの直後に書く必要がある。
    @PostMapping("/add")
    public String addComment(Authentication loginUser, @Validated @ModelAttribute Comment comment, BindingResult result, SiteUser siteUser, Model model)  {
//		currentUserはPOSTMAPPINGにもあるのでできれば処理を統一したい
        String currentUser = loginUser.getName();
        
//        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("commentwithreplys", commentService.findAllCommentWithreplys());
        model.addAttribute("currentUser", currentUser);
        
        //入力にerrorがある場合失敗
        if (result.hasErrors()) {
    		return "commentlist";
    	}

        //入力に問題がなければコメントをSAVE
        comment.setUsername(currentUser);
        comment.setPostDateTime(LocalDateTime.now());
        commentService.save(comment);

        return "redirect:/";
    }
    
    //Bindingresultは、commentのエラーを拾いたいならcommentの直後に書く必要がある。
    @PostMapping("/reply/{id}")
    public String addreply(@PathVariable Long id, Authentication loginUser, @Validated @ModelAttribute Comment comment, BindingResult result, SiteUser siteUser, Model model)  {
//        model.addAttribute("comments", commentService.findAll());
        model.addAttribute("commentwithreplys", commentService.findAllCommentWithreplys());
        
        if (result.hasErrors()) {
    		return "commentlist";
    	}
        
//      入力された情報をnewしたコメントオブジェクトに入力させる（ParentIdがある＝reply）
        Comment reply = new Comment();
        String postingUser = loginUser.getName();
        reply.setUsername(postingUser);
        LocalDateTime postLocalDateTime = LocalDateTime.now();
        reply.setPostDateTime(postLocalDateTime);
        Long parentId = id;
        reply.setParentCommentId(parentId);
        String content = comment.getContent();
        reply.setContent(content);
        commentService.save(reply);
        
        return "redirect:/";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id, Authentication loginUser,@ModelAttribute Comment comment,@ModelAttribute("user") SiteUser user) {
    	//ここだけcurrentUserの意味が違うので気持ち悪い、後でどちらかに統一する
    	SiteUser currentUser = siteUserService.findByUsername(loginUser.getName());
        Comment deleteComment = commentService.findById(id);
                    	
        //ADMINか作成者の場合成功、なぜか「.isAdmin（）」では通らないので文字列で比較している」
        if (currentUser.getRole().equals("ADMIN") || currentUser.getUsername().equals(deleteComment.getUsername())) {
           	commentService.deleteById(id);
        	return "redirect:/";
    	}
        
        throw new AccessDeniedException("Access is denied");
		
    }
    

    @GetMapping("/admin/userlist")
    public String showAdminList(Model model) {
        model.addAttribute("users", siteUserService.findAll());
        return "userlist";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("user") SiteUser user) {
        return "register";
    }

    @PostMapping("/register")
    public String process(@Validated @ModelAttribute("user") SiteUser user,
            BindingResult result) {

        if (result.hasErrors()) {
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.isAdmin()) {
            user.setRole(Role.ADMIN.name());
        } else {
            user.setRole(Role.USER.name());
        }
        siteUserService.save(user);

        return "redirect:/admin/userlist?register";
    }
}
