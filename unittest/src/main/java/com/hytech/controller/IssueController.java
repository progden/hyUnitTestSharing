package com.hytech.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value="/{id}", method=RequestMethod.GET, headers="ACCEPT=applicatoin/json")
	@ResponseBody
	public ResponseEntity<String> queryIssueById(HttpServletRequest req, @PathVariable("id") Integer id){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String json = issueService.getIssue(id);
		
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<String> insertIssue(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>("{\"status\":\"OK\"}", headers, HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<String> updateIssue(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>("{\"status\":\"OK\"}", headers, HttpStatus.OK);
	}
	
}
