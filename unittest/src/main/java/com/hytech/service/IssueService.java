package com.hytech.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.hytech.dao.IssueMapper;
import com.hytech.model.Issue;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueService {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	public IssueService() {
		super();
	}

	public String getAllIssue() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IssueMapper maper = sqlSession.getMapper(IssueMapper.class);
		List<Issue> rs = maper.selectAll();
		String arr = Issue.toJsonArray(rs);
		sqlSession.close();
		return arr;
	}

	public String getIssue(Integer id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IssueMapper maper = sqlSession.getMapper(IssueMapper.class);
		Issue rs = maper.selectByPrimaryKey(id);
		String json = rs.toJson();
		sqlSession.close();
		return json;
	}

	public String addIssue(Issue issue){

		SqlSession sqlSession = sqlSessionFactory.openSession();
		IssueMapper maper = sqlSession.getMapper(IssueMapper.class);
		int rs = maper.insert(issue);
		sqlSession.close();
		return (rs == 1)? "{\"status\": \"OK\"}":"{\"status\":\"Fail\"}";
	}
	
	public String updateIssue(Issue issue){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		IssueMapper maper = sqlSession.getMapper(IssueMapper.class);
		int rs = maper.updateByPrimaryKey(issue);
		sqlSession.close();
		return (rs == 1)? "{\"status\": \"OK\"}":"{\"status\":\"Fail\"}";	
	}
}