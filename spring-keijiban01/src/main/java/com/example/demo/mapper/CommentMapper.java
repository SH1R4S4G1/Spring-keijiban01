package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.Comment;

@Mapper
public interface CommentMapper {
	
	@Select("SELECT * FROM comment")
	List<Comment> selectAll();
	
	@Select({
		"SELECT * FROM comment",
		"WHERE user_name = #{byusername}"
	})
	Comment selectByUsername(String byusername);
	
	@Select({
		"SELECT * FROM comment",
		"WHERE id = #{id}"
	})
	Comment selectByPrimaryKey(Long id);
	
	@Select({"SELECT * FROM comment",
			"WHERE parent_comment_id IS NULL"
		})
	List<Comment> selectAllCommentNotreply();
	
	@Insert({
		"INSERT INTO comment(content, user_name, post_date_time, parent_comment_id)",
		"VALUES(#{content}, #{username}, #{postDateTime}, #{parentCommentId})"
	})
	int insert(Comment record);
	
	@Update({
		"UPDATE comment",
		"SET content = #{content}, user_name = #{username}, post_date_time = #{postDateTime}",
		"WHERE id = #{id}"
	})
	int updateByPrimaryKey(Comment record);
	
	@Delete({
		"DELETE FROM comment",
		"WHERE id = #{id}"
	})
	int deleteByPrimaryKey(Long id);
	

}
