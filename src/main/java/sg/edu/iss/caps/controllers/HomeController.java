package sg.edu.iss.caps.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.Student;


@Controller
public class HomeController extends TemplateController{
	

	@RequestMapping("/")
	public String viewHomePage(Model model) {
		return "home";
	}
	

}
