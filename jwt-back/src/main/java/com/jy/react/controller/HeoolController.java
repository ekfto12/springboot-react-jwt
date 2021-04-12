package com.jy.react.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeoolController {

	@RequestMapping({"/hello"})
	public String firstPage() {
		return "Hello. you have valid JWT (JSon Web Token)!";
	}
}
