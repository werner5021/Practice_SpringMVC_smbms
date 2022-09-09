package com.werner.controller.login;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.werner.pojo.User;
import com.werner.service.login.LoginServiceImpl;
import com.werner.util.Constants;

@Controller
public class LoginController {	
	
	@Autowired
	private LoginServiceImpl loginService;
	
	//登入
	@PostMapping("/login")
	public String login(Model model, HttpSession session,String userCode, String userPassword) {
		User user = loginService.findLoginUser(userCode, userPassword);
		
		if(user != null) {
			session.setAttribute(Constants.USER_SESSION, user);
			return "frame";  
		}else {
			model.addAttribute(Constants.ERROR, "使用者名稱或密碼錯誤");
			return "redirect:/login.jsp";  
		}
		
	}	
	
 
	
}
