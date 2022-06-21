package sg.edu.iss.caps.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.AppPage;
import sg.edu.iss.caps.repositories.CourseRepository;
import sg.edu.iss.caps.repositories.LecturerRepository;
import sg.edu.iss.caps.repositories.StudentCourseRepository;
import sg.edu.iss.caps.repositories.StudentRepository;

@Controller
public class TemplateController {
	
	protected StudentRepository studrepo;
	protected CourseRepository courserepo;
	protected LecturerRepository lecrepo;
	protected StudentCourseRepository studcourseRepo;
	
}
