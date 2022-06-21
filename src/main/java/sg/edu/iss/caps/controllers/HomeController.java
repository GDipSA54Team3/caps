package sg.edu.iss.caps.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.LoginBag;
import sg.edu.iss.caps.model.LoginUser;
import sg.edu.iss.caps.model.Role;


@Controller
public class HomeController{
	

	@RequestMapping("/")
	public String viewHomePage(Model model, HttpSession session) {
		
		LoginUser user = new LoginUser("", "", Role.TO_LOGIN);
		LoginBag lb = new LoginBag(user);
		
		System.out.println(lb.getLoggeduser().getUsername());
		
		model.addAttribute("user", user);
		session.setAttribute("loggeduser", lb);
		
		return "home";
	}

}
