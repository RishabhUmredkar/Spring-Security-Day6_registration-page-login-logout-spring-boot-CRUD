package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	

	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable("id") Long id, Model model) {
	    User user = repo.findById(id).orElse(null);

	    // Assuming you have a getPassword() method in your User entity
	    String plainPassword = user.getPassword();
	    user.setPassword(plainPassword);

	    model.addAttribute("user", user);
	    return "edit"; // Assuming you have an edit_user.html template
	}


    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        user.setId(id);
        
        // If you want to update the password, use BCryptPasswordEncoder here
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        repo.save(user);
        return "redirect:/list_users"; // Adjusted to match your list mapping
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        repo.deleteById(id);
        return "redirect:/list_users"; // Adjusted to match your list mapping
    }
	
}
