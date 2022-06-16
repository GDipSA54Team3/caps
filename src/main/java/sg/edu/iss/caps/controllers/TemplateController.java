package sg.edu.iss.caps.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TemplateController {
	
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
		
		return "home";
	}
}
