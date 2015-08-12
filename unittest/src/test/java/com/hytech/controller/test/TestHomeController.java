package com.hytech.controller.test;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.hytech.controller.HomeController;

public class TestHomeController {

	@Test
	public void testIndex() throws IOException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		ModelAndView rs = new HomeController().index(request, response);
		
		Assert.assertEquals("home", rs.getViewName());
	}
}
