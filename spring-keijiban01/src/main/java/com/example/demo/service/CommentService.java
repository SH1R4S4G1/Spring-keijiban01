package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.model.Comment;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
	
	private final CommentMapper commentMapper;
	
	public List<Comment> findAll() {
		return commentMapper.selectAll();
	};
	
	public Comment findById(Long id) {
		return commentMapper.selectByPrimaryKey(id);
	}
	
	public void save(Comment comment) {
		if(comment.getId()==null) {
			commentMapper.insert(comment);
		} else {
			commentMapper.updateByPrimaryKey(comment);
		}
	}
	
	public void deleteById(Long id) {
		commentMapper.deleteByPrimaryKey(id);
	}
	
	//メモリが大変なことになりそう
	public List<Comment> findAllCommentWithreplys() {
		List<Comment> comments = commentMapper.selectAll();
		for(Comment comment : comments) {
			List<Comment> replys = commentMapper.selectAll();
				for(Comment reply : replys) {
					if(comment.getId() == reply.getParentCommentId()) {
						comment.getReplys().add(reply);
					}
				}
			}
		return comments;
		}
	}

	
	
