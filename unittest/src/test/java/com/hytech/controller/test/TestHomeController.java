package com.hytech.controller.test;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.hytech.controller.HomeController;

public class TestHomeController {

	/**
	 * 測試直接存取 controller 是否有回傳對的頁面
	 * 
	 * 檢驗是否是回傳 home 頁面
	 * @throws IOException
	 */
	@Test
	public void testIndex() throws IOException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		ModelAndView rs = new HomeController().index(request, response);
		
		Assert.assertEquals("home", rs.getViewName());
	}
}
