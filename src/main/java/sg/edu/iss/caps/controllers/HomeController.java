package sg.edu.iss.caps.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


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
