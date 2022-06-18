package sg.edu.iss.caps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.CourseStatus;
import sg.edu.iss.caps.model.StudentCourse;
import sg.edu.iss.caps.services.CourseService;
import sg.edu.iss.caps.services.StudentCourseService;
import sg.edu.iss.caps.services.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private CourseService cs;
	
	@Autowired
	private StudentCourseService scs;
	
	@Autowired
	private StudentService ss;
	
	@GetMapping("/main")
	public String mainPage() {
		return "studenthome";
	}
	
	@GetMapping("/courseList")
	public String showCourseList(Model model) {
		model.addAttribute("listCourses", cs.getAllCourse());
		return "studentcourselist";
	}
	
	@PostMapping("/searchCourseList")
	public String searchCourse(Model model, @Param("search") String search) {
		model.addAttribute("listCourses", cs.returnCourseByName(search));
		return "studentcourselist";
	}
	
	// for testing purposes only
	@GetMapping("/registerCourse/{id}")
	public String regCourse(Model model, @PathVariable("id") String id) {
		model.addAttribute("studentList", ss.getAllStudents());
		model.addAttribute("courseObject", cs.getCourseById(id));
		return "studentregistertest";
	}
	
	// for testing purposes only
	@PostMapping("/confirmRegister")
	public String confirmReg(@Param("courseId") String courseId, @Param("studentId") String studentId) {
		StudentCourse test = new StudentCourse(ss.getStudentById(studentId), cs.getCourseById(courseId), CourseStatus.ENROLLED);
		scs.newStudentCourse(test);
		return "redirect:/student/courseList";
	}
	
	//for testing purposes only
	@GetMapping("/findMyCourses")
	public String myCourses(Model model) {
		model.addAttribute("studentList", ss.getAllStudents());
		return "studentcourses";
	}
	
	//for testing purposes only
	@PostMapping("/myCourses")
	public String myCourses(@Param("id") String id, Model model) {
		model.addAttribute("studentList", ss.getAllStudents());
		model.addAttribute("studentCourses", cs.getCoursesByStudId(id));
		return "studentcourses";
	}
}
