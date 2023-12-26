package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;


@Controller
public class HomeController {

	@Autowired
	private UserRepository repo; 
	
	@RequestMapping("/")
	public String viewHomePage() {
		return "index.html";
	}
	
	@RequestMapping("/register")
	public String ShowSignupForm(Model model)
	{
		model.addAttribute("user", new User());
		return "signup_form.html";
		
	}
	
	@RequestMapping("/process_register")
	public String ProcessRegistration(User user)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repo.save(user);
		return "register_success";
		
	}
	
	@RequestMapping("/list_users")
	public String viewUserList(Model model)
	{
		List<User> listUser = repo.findAll();
		model.addAttribute("listUser", listUser);
		return "users";
	}
}
