package com.werner.controller.login;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.werner.util.Constants;

@Controller
public class LogoutController {
	
	//登出
	@GetMapping("/logout")
	public String logout(Model model, HttpSession session) {
		
		session.removeAttribute(Constants.USER_SESSION);
		return "redirect:/login.jsp";
	}

	
}
