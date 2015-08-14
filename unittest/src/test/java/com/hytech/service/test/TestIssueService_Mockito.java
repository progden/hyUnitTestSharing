package com.hytech.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hytech.config.MvcConfiguration;
import com.hytech.dao.IssueMapper;
import com.hytech.exception.CustomException;
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
	
	/**
	 * 	建立初始用資料
	 */
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
	
	/**
	 * 	測試取得所有 Issue 功能
	 *  檢驗資料筆數是否正確
	 *  
	 *  再怎麼驗證，都會是正確的。可重複執行。
	 */
	@Test
	public void testGetAllIssue() {
		String issueJson = service.getAllIssue();
		List<Issue> issueList = (List<Issue>) BaseModel.fromJsonArrayToModel(issueJson, Issue.class);
		
		assertEquals(3, issueList.size());
		// 資料是null會如何？
	}
	
	/**
	 * 測試查詢一筆 Issue 功能
	 * 
	 * 
	 */
	@Test
	public void testGetIssue() {
		String issueJson = service.getIssue(1);
		Issue issue = BaseModel.fromJsonToOwner(issueJson, Issue.class);

		assertEquals("新建立", issue.getStatus());
		assertEquals("Title1", issue.getTitle());
		assertEquals("Tester1", issue.getCreater());
		assertEquals("Tester2", issue.getOwner());
	}
	
	/**
	 * service 做的工作是呼叫 dao 跟 處理業務邏輯
	 * 
	 * 這裏的業務邏輯是 
	 *   如果沒有傳遞 key 參數會查全部資料
	 *   如果有傳遞 key 參數會依照 key 來查詢
	 */
	@Test
	public void testConditionQuery(){
		Map<String, String> param = new HashMap<String, String>();
//		param.put("key", "ontent");
		try {
			String issueJson = null;
			issueJson = service.getIssueByCondition(param);
		} catch (CustomException e) {
			Assert.assertTrue(true); // should not go here, cause we have data in database!?!?
		}
//		Mockito.verify(sqlSessionFactory.openSession().getMapper(IssueMapper.class)).selectAll(); // SAME as next line
		Mockito.verify(sqlSessionFactory.openSession().getMapper(IssueMapper.class), Mockito.times(1)).selectAll();
		
	}
	@Test
	public void testConditionQuery2(){
		Map<String, String> param = new HashMap<String, String>();
		param.put("key", "ontent");
		try {
			String issueJson = service.getIssueByCondition(param);
		} catch (CustomException e) {
			Assert.assertTrue(true); // should not go here, cause we have data in database!?!?
		}
//		Mockito.verify(sqlSessionFactory.openSession().getMapper(IssueMapper.class)).selectByCondition(Mockito.anyMap());
		Mockito.verify(sqlSessionFactory.openSession().getMapper(IssueMapper.class), Mockito.times(1)).selectByCondition(Mockito.anyMap());
	}
	
	private List<Issue> getIssueMockData() {
		List<Issue> result = new ArrayList<Issue>();
		for (int i = 1; i <= 3; i++) {
			Issue issue = new Issue();
			issue.setId(i);
			issue.setCreater(String.format("Tester%d", i));
			issue.setOwner(String.format("Tester%d", i+1));
			issue.setStatus("新建立");
			issue.setTitle(String.format("Title%d", i));
			issue.setContent(String.format("Content%d", i));
			result.add(issue);
		}
		
		return result;
	}
	
}
