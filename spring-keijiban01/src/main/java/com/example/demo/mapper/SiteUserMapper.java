package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.SiteUser;

@Mapper
public interface SiteUserMapper {
	
	@Select("SELECT * FROM site_user")
	List<SiteUser> selectAll();

	@Select({
		"SELECT * FROM site_user",
		"WHERE id = #{id}"
	})
	SiteUser selectByPrimaryKey(Long id);
	
	@Select({
		"SELECT * FROM site_user",
		"WHERE user_name = #{username}"
	})
	SiteUser selectByUsername(String username);
	
	@Insert({
		"INSERT INTO site_user(user_name, password, email, admin, role, active)",
		"VALUES(#{username}, #{password}, #{email}, #{admin}, #{role}, #{active})"
	})
	int insert(SiteUser record);
	
	@Update({
		"UPDATE site_user",
		"SET user_name = #{username}, password = #{password}, email = #{email}, admin = #{admin}, role = #{role}",
		"WHERE id = #{id}"
	})
	int updateByPrimaryKey(SiteUser record);
	
	@Delete({
		"DELETE FROM site_user",
		"WHERE id = #{id}"
	})
	int deleteByPrimaryKey(Long id);
}
