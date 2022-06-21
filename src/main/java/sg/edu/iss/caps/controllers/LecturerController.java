package sg.edu.iss.caps.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.LoginBag;
import sg.edu.iss.caps.model.LoginUser;
import sg.edu.iss.caps.model.Role;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.repositories.CourseRepository;
import sg.edu.iss.caps.services.CourseService;
import sg.edu.iss.caps.services.LecturerService;
import sg.edu.iss.caps.services.StudentService;


@Controller
@RequestMapping("/lecturer")
@SessionAttributes("loggeduser")
public class LecturerController {


	@Autowired
	private CourseService corserv;
	
	@Autowired
	private StudentService stuserv;
	
	@RequestMapping("/view-courses")
	public String viewLecCourses(
			Model model, HttpSession session	) {
		
		String userId  = getCurrentUserId(session);
		
		//To get the courses of currently logged in lecturer
		List<Course> courses = corserv.getCoursesByLecturerId(userId);
		
		//Addcourses to listCourses for display
		model.addAttribute("listCourses",  courses);
																							
		return "leccourses";		
	}	
	
	@PostMapping("/search-courses")
	public String searchCourses(@Param("name") String name, Model model,
			HttpSession session) {
		
		String userid  = getCurrentUserId(session);
		//find courses by Course name and lecturer if searched with non empty string
		if (name != "") {
			List<Course> courses = corserv.findByLecturerAndCourse(userid, name);			
		
		model.addAttribute("listCourses", courses);
		
		return "leccourses";
		}
		//list all courses for the lecturer if searched with empty string
		else 
			return "redirect:/lecturer/view-courses";
	}
	
	//Returns the userId of the currently loggen in user
	public String getCurrentUserId (HttpSession session){
		
		LoginBag lb =(LoginBag) session.getAttribute("loggeduser");
		String userId = lb.getLoggeduser().getUserId();
		
		return userId;
		
	}
	
	@RequestMapping("/view-course-student-list/{id}")
	public String viewCourseStudentView(@PathVariable(value = "id") String courseId, Model model) {
		
		List<Student> students = stuserv.getStudentByCourse(courseId);
		model.addAttribute("listStudents", students);	
			
		return "home"; //Just temp only correct page us under devolopment
	}

}
