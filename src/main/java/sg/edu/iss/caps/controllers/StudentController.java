package sg.edu.iss.caps.controllers;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.exceptions.DuplicateException;
import sg.edu.iss.caps.model.*;
import sg.edu.iss.caps.services.CourseService;
import sg.edu.iss.caps.services.StudentCourseService;
import sg.edu.iss.caps.services.StudentService;
import sg.edu.iss.caps.utilities.CalculateGPA;
import sg.edu.iss.caps.utilities.SortByCourseName;
import sg.edu.iss.caps.utilities.SortByStudCourseName;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private CourseService courseServ;

	@Autowired
	private StudentCourseService studCourseServ;

	@Autowired
	private StudentService studServ;
	
	@Autowired
	private CalculateGPA cgpa;

	@GetMapping("/courseList")
	public String showCourseList(Model model, HttpSession session) {
		LoginBag user = (LoginBag) session.getAttribute("loggeduser");
		List<Course> courseList = courseServ.getAllCourse();
		Collections.sort(courseList, new SortByCourseName());
		model.addAttribute("listCourses", courseList);
		model.addAttribute("student", studServ.getStudentById(user.getLoggeduser().getUserId()));
		model.addAttribute("studRegisteredCourses", courseServ.findCoursesByStudId(user.getLoggeduser().getUserId()));
		return "studentcourselist";
	}

	@PostMapping("/searchCourseList")
	public String searchCourse(Model model, @Param("search") String search, HttpSession session) {
		LoginBag user = (LoginBag) session.getAttribute("loggeduser");
		model.addAttribute("listCourses", courseServ.getAllCourse());
		List<Course> courseList = courseServ.findCoursesByName(search);
		Collections.sort(courseList, new SortByCourseName());
		model.addAttribute("listCourses", courseList);
		model.addAttribute("student", studServ.getStudentById(user.getLoggeduser().getUserId()));
		model.addAttribute("studRegisteredCourses", courseServ.findCoursesByStudId(user.getLoggeduser().getUserId()));
		return "studentcourselist";
	}

	@GetMapping("/registerCourse/{studentId}/{courseId}")
	public String regCourse(Model model, @PathVariable("courseId") String courseId, @PathVariable("studentId") String studentId) throws DuplicateException {
		List<Course> studentRegCourses = courseServ.findCoursesByStudId(studentId);
		if (!studentRegCourses.contains(courseServ.getCourseById(courseId)) && courseServ.isCapacityOk(courseId)) {
			StudentCourse sc = new StudentCourse(studServ.getStudentById(studentId), courseServ.getCourseById(courseId), Grade.NA, CourseStatus.ENROLLED);
			studCourseServ.newStudentCourse(sc);
		} else {
			throw new DuplicateException(String.format("\n\n\n ErrorRegistrationFailed: Student is already enrolled in \"%s\" or course is fully booked \n\n", courseServ.getCourseById(courseId).getCourseName()));
		}
		return "redirect:/student/courseList";
	}

	@GetMapping("/myCourses")
	public String myCourses(HttpSession session, Model model) {
		LoginBag user = (LoginBag) session.getAttribute("loggeduser");
		List<StudentCourse> studCourseList = studServ.findStudCoursesByStudId(user.getLoggeduser().getUserId());
		Collections.sort(studCourseList, new SortByStudCourseName());
		model.addAttribute("studentCourses", studCourseList);
		if (studCourseList.isEmpty()) {
			model.addAttribute("gpa", "Not available");
		}else {
			model.addAttribute("gpa", cgpa.calculateGpa(user.getLoggeduser().getUserId(), studCourseList));
			System.out.println(cgpa.calculateGpa(user.getLoggeduser().getUserId(), studCourseList));
		}
		return "studentcourses";
	}
}
