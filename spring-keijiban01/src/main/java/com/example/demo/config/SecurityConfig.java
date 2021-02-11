package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserDetailsService userDetailService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**","/css/**","/webjars/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/login").permitAll()
//				H2コンソールへアクセスしたいとき
//				.antMatchers("/login", "/register","/console/**").permitAll()
//				表示はしてもよい
//				.antMatchers("/admin/**").hasRole(Role.ADMIN.name())
				.anyRequest().authenticated()
			.and()
				.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/")
			.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.and()
				.rememberMe();
//		H2コンソールへアクセスしたいとき
//		http.csrf().disable();
//		http.headers().frameOptions().disable();

	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}
	

	
	//ここはセキュリティ上あとで消す
//	@Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth
//            // メモリ内認証を設定
//            .inMemoryAuthentication()
//            // "user"を追加
//            .withUser("user")
//            // "password"をBCryptで暗号化
//            .password(passwordEncoder().encode("password"))
//             // 権限（ロール）を設定
//            .authorities("ROLE_USER");
//    }
	
	
}
