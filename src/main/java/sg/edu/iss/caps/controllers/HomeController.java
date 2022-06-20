package sg.edu.iss.caps.controllers;




import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.*;
import sg.edu.iss.caps.services.*;


@Controller
@RequestMapping("/")
public class HomeController extends TemplateController{
		
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
