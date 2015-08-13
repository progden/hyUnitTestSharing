package com.hytech.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hytech.config.MvcConfiguration;
import com.hytech.dao.IssueMapper;
import com.hytech.model.BaseModel;
import com.hytech.model.Issue;
import com.hytech.service.IssueService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes=MvcConfiguration.class)
public class TestIssueService_Mockito {
	
	@InjectMocks
	private IssueService service;
	
	@Mock
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		SqlSession sessionMock = mock(SqlSession.class);
		IssueMapper issueMapper = mock(IssueMapper.class);
		
		// stubbing
		when(sqlSessionFactory.openSession()).thenReturn(sessionMock);
		when(sqlSessionFactory.openSession().getMapper(IssueMapper.class)).thenReturn(issueMapper);
		when(sqlSessionFactory.openSession().getMapper(IssueMapper.class).selectAll()).thenReturn(getIssueMockData());
		when(sqlSessionFactory.openSession().getMapper(IssueMapper.class).selectByPrimaryKey(1)).thenReturn(getIssueMockData().get(0));
		when(sqlSessionFactory.openSession().getMapper(IssueMapper.class).selectByPrimaryKey(2)).thenReturn(getIssueMockData().get(1));
		when(sqlSessionFactory.openSession().getMapper(IssueMapper.class).selectByPrimaryKey(3)).thenReturn(getIssueMockData().get(2));
	}
	
	@Test
	public void testGetAllIssue() {
		String issueJson = service.getAllIssue();
		List<Issue> issueList = (List<Issue>) BaseModel.fromJsonArrayToModel(issueJson, Issue.class);
		
		assertEquals(3, issueList.size());
		
		// 資料是null會如何？
	}
	
	@Test
	public void testGetIssue() {
		String issueJson = service.getIssue(1);
		Issue issue = BaseModel.fromJsonToOwner(issueJson, Issue.class);
		
		assertEquals("Tester1", issue.getCreater());
	}
	
	private List<Issue> getIssueMockData() {
		List<Issue> result = new ArrayList<Issue>();
		for (int i = 1; i <= 3; i++) {
			Issue issue = new Issue();
			issue.setId(i);
			issue.setCreater(String.format("Tester%s", i));
			result.add(issue);
		}
		
		return result;
	}
	
}
