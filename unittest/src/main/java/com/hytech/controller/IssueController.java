package com.hytech.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hytech.exception.CustomException;
import com.hytech.model.Issue;
import com.hytech.service.IssueService;


@Controller
@RequestMapping("/issue")
public class IssueController {
	@Autowired IssueService issueService;

	@RequestMapping(method=RequestMethod.GET, headers="ACCEPT=applicatoin/json")
	@ResponseBody
	public ResponseEntity<String> queryIssue(HttpServletRequest req){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String json = issueService.getAllIssue();
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, params="condition")
	@ResponseBody
	public ResponseEntity<String> queryIssue(HttpServletRequest req, @RequestParam Map<String, String> params){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		String json;
		try {
			json = issueService.getIssueByCondition(params);
		} catch (CustomException e) {
			// FIXME log error here
			
			json = issueService.getAllIssue();
		}
		
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET, headers="ACCEPT=applicatoin/json")
	@ResponseBody
	public ResponseEntity<String> queryIssueById(HttpServletRequest req, @PathVariable("id") Integer id){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String json = issueService.getIssue(id);
		
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<String> insertIssue(HttpServletRequest req, HttpServletResponse resp){
		HttpHeaders headers = new HttpHeaders();
		Issue i = new Issue();
		i.setStatus("新建立");
		i.setTitle(req.getParameter("title"));
		i.setContent(req.getParameter("content"));
		i.setCreater(req.getParameter("creater"));
		i.setOwner(req.getParameter("owner"));
		i.setDtCreate(new Date());
		i.setDtLastupdate(new Date());
		System.out.println(i);
		headers.add("Content-Type", "application/json; charset=utf-8");
		String rs = issueService.addIssue(i);
		return new ResponseEntity<String>(rs, headers, HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<String> updateIssue(){
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>("{\"status\":\"OK\"}", headers, HttpStatus.OK);
	}
	
}
