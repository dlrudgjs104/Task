package com.nhnacademy.exam.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@PostMapping("/http://133.186.241.167:8200/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		return null;
	}
}
