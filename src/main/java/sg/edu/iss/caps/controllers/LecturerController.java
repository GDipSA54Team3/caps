package sg.edu.iss.caps.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.services.CourseService;
import sg.edu.iss.caps.services.LecturerService;


@Controller
@RequestMapping("/lecturer")
public class LecturerController {


	@Autowired
	private LecturerService lecserv;
	
	@Autowired
	private CourseService corserv;
		
	@RequestMapping("/view-courses")
	public String viewLecCourses(Model model) {
		
		model.addAttribute("listLecturers", corserv.getCoursesByLecturerId("40288af3817c689301817c6896ee0005")); 
																							
		return "leccourses";		
	}	

}
