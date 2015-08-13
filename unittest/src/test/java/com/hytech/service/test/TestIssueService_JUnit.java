package com.hytech.service.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.gson.Gson;
import com.hytech.config.MvcConfiguration;
import com.hytech.model.BaseModel;
import com.hytech.model.Issue;
import com.hytech.service.IssueService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes=MvcConfiguration.class)
public class TestIssueService_JUnit {
	
	@Autowired
	protected IssueService service;
	
	@Test
	public void testGetAllIssue() {
		String issueJson = service.getAllIssue();
		List<Issue> issueList = (List<Issue>) BaseModel.fromJsonArrayToModel(issueJson, Issue.class);
		
		assertEquals(2, issueList.size());
	}
	
	@Test
	public void testGetIssue() {
		String issueJson = service.getIssue(1);
		Issue issue = BaseModel.fromJsonToOwner(issueJson, Issue.class);
		
		assertEquals("success", issue.getStatus());
		assertEquals("test", issue.getTitle());
		assertEquals("shoghi", issue.getCreater());
		assertEquals("dennis", issue.getOwner());
	}
	
	@Test
	public void testAddIssue() {
		Issue issue = new Issue();
		issue.setCreater("robert");
		service.addIssue(issue);
	}
	
	@Test
	public void testUpdateIssue() {
		// 取得要更新的資料
		String issueJson = service.getIssue(3);
		Issue issue = BaseModel.fromJsonToOwner(issueJson, Issue.class);
		issue.setOwner("shoghi");
		
		// 更新
		service.updateIssue(issue);
		
		// 取得更新後的資料
		issueJson = service.getIssue(3);
		issue = BaseModel.fromJsonToOwner(issueJson, Issue.class);
		
		// 驗證
		assertEquals("shoghi", issue.getOwner());
	}
	
}
