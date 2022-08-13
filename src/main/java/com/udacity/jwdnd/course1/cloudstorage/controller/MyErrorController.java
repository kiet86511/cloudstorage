package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;

public class MyErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE); 
		if(status != null) {
			int statusCode = Integer.parseInt(status.toString());
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				return "404";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "403";
			} else {
				return "400";
			}
		}
		return "error";
	}

}
