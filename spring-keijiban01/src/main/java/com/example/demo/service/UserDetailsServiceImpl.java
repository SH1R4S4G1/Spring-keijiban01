package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.SiteUserMapper;
import com.example.demo.model.*;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final SiteUserMapper siteUserMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		SiteUser user = findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username+" not found.");
		}
		return createUserDetails(user); 
	}
	
	public User createUserDetails(SiteUser user) {
		Set<GrantedAuthority> grantedAuthories = new HashSet<>();
		grantedAuthories.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
		
		return new User(user.getUsername(), user.getPassword(), grantedAuthories);
	}
	
	public List<SiteUser> findAll() {
		return siteUserMapper.selectAll();
	};
	
	public SiteUser findByUsername(String username) {
		return siteUserMapper.selectByUsername(username);
	}
	
	public SiteUser findById(Long id) {
		return siteUserMapper.selectByPrimaryKey(id);
	}
	
	public void save(SiteUser siteUser) {
		if(siteUser.getId()==null) {
			siteUserMapper.insert(siteUser);
		} else {
			siteUserMapper.updateByPrimaryKey(siteUser);
		}
	}
	
	public void deleteById(Long id) {
		siteUserMapper.deleteByPrimaryKey(id);
	}
}
