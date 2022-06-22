package sg.edu.iss.caps.controllers;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import sg.edu.iss.caps.exceptions.DuplicateException;
import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.CourseStatus;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.LoginUser;
import sg.edu.iss.caps.model.Role;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.StudentCourse;
import sg.edu.iss.caps.services.CourseService;
import sg.edu.iss.caps.services.LecturerService;
import sg.edu.iss.caps.services.StudentCourseService;
import sg.edu.iss.caps.services.StudentService;
import sg.edu.iss.caps.utilities.SortByCourseName;
import sg.edu.iss.caps.utilities.SortByStudentName;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private LoginController loginCon;
	
	
	/*
	 * REPOSITORIES TO INJECT STARTS HERE
	 */

	@Autowired
	private LecturerService lecserv;
	
	@Autowired
	private CourseService couserv;
	
	@Autowired
	private StudentService studServ;
	
	@Autowired
	private StudentCourseService studCourseServ;
	
	
	/*
	 * REPOSITORIES TO INJECT ENDS HERE
	 */
	
	
	
	/*
	 * LECTURER METHODS START HERE
	 */
	
	@RequestMapping("/manage-lecturers")
	public String viewHomePage(Model model) {
		model.addAttribute("listLecturers", lecserv.getAllLecturers());
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		return "managelecturers";
	}
	
	@GetMapping("/add-new-lecturer")
	public String newLecturer(Model model) {
		Lecturer lecturer = new Lecturer();
		
		model.addAttribute("lecturer", lecturer);
		
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		return "newLecturer";
	}
	
	@PostMapping("/save-lecturer")
	public String saveLecturer(@ModelAttribute("lecturer") Lecturer lecturer) {
		lecserv.saveLecturer(lecturer);
		
		return "redirect:/admin/manage-lecturers";
	}
	

	@GetMapping("/update-lecturer/{id}")
	public String updateLecturer(@PathVariable(value = "id") String id, Model model) {
		
		Lecturer lecturer = lecserv.getLecturerById(id);
		
		System.out.println("This code works");
		
		model.addAttribute("lecturer", lecturer);
		
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		return "updateLecturer";
	}
	
	@GetMapping("/delete-lecturer/{id}")
	public String deleteLecturer(@PathVariable(value = "id") String id, Model model) {
		
		lecserv.deleteLecturerById(id);
		
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		return "redirect:/admin/manage-lecturers";
	}
	
	@PostMapping("/search-lecturers")
	public String searchLecturers(@Param("name") String name, Model model) {
		List<Lecturer> listLecturers = lecserv.returnLecturerByName(name);
		
		model.addAttribute("listLecturers", listLecturers);
		
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		return "managelecturers";
	}
	
	@GetMapping("/view-lecturer-courses/{id}")
	public String viewLecturerCourses(@PathVariable(value = "id") String id, Model model) {
		
		Lecturer lecturer = lecserv.getLecturerById(id);
		
		List<Course> listCourse = lecturer.getCourses();
		
		model.addAttribute("listCourse", listCourse);
		model.addAttribute("lecturer", lecturer);
		
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		return "viewLecturerCourses";
	}
	
	/*
	 * LECTURER METHODS END HERE
	 */
	
	
	//COURSE METHODS STARTS HERE
	@RequestMapping("/manage-course")
	public String viewCoursePage(Model model, HttpSession session) {
		List<Course> courseList = couserv.getAllCourse();
		Collections.sort(courseList, new SortByCourseName());
		model.addAttribute("listCourse", courseList);
		return "manageCourse";
	}
	
	@GetMapping("/add-new-course")
	public String newCourse(Model model, HttpSession session) {
		Course course = new Course();
		model.addAttribute("course", course);
		return "newupdatecourse";
	}
	
	@PostMapping("/search-courses")
	public String searchCourses(@Param("name") String name, Model model) {
		List<Course> listCourse = couserv.findCoursesByName(name);
		Collections.sort(listCourse, new SortByCourseName());
		model.addAttribute("listCourse", listCourse);
		return "manageCourse";
	}
	
	@PostMapping("/save-course")
	public String saveCourse(@ModelAttribute("course") @Valid Course course, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "newupdatecourse";
		}
		couserv.saveCourse(course);
		return "redirect:/admin/manage-course";
	}
	
	@GetMapping("/edit-course/{id}")
	public String editCourseById(@PathVariable(value = "id") String id, Model model) {
		Course course = couserv.getCourseById(id);
		model.addAttribute("course", course);
		return "newupdatecourse";
	}
	
	@GetMapping("/delete-course/{id}")
	public String removeCourse(@PathVariable(value = "id") String id, Model model) {
		couserv.deleteCourseById(id);
		return "redirect:/admin/manage-course";
	}
	//COURSE METHODS ENDS HERE
	
	//ENROLLMENT METHODS STARTS HERE
	@GetMapping("/view-student-courses/{courseId}")
	public String viewStudCourses(@PathVariable("courseId") String courseId, Model model) {
		List<Student> listStudent = studServ.getAllStudents();
		Collections.sort(listStudent, new SortByStudentName());
		model.addAttribute("listStudent", listStudent);
		model.addAttribute("listExistStudent", studServ.getStudentByCourse(courseId));
		List<StudentCourse> listStudCourse = couserv.getStudCoursesByCourseId(courseId);
		model.addAttribute("listStudCourse", listStudCourse);
		model.addAttribute("course", couserv.getCourseById(courseId));
		return "manageenrollment";
	}
	
	@PostMapping("/enroll-student")
	public String enrollStudent(@Param("selectedStudentId") String selectedStudentId, @Param("courseId") String courseId) throws DuplicateException {
		if (couserv.isCapacityOk(courseId)) {
			StudentCourse sc = new StudentCourse(studServ.getStudentById(selectedStudentId), couserv.getCourseById(courseId), CourseStatus.ENROLLED);
			studCourseServ.newStudentCourse(sc);
		} else {
			throw new DuplicateException(String.format("\n\n\n ErrorRegistrationFailed: Student is already enrolled in \"%s\" or course is fully booked \n\n", couserv.getCourseById(courseId).getCourseName()));
		}
		return "redirect:/admin/view-student-courses/"+courseId;
	}
	
//	@PostMapping("/search-enrollstudent")
//	public String searchEnrollStudent(@Param("courseId") String courseId, @Param("name") String name, Model model) {
//		model.addAttribute("listStudent", studServ.getAllStudents());
//		model.addAttribute("listExistStudent", studServ.getStudentByCourse(courseId));
//		model.addAttribute("listStudCourse", listStudCourse);
//		model.addAttribute("course", couserv.getCourseById(courseId));
//		return "manageenrollment";
//	}
	
	@GetMapping("/deleteEnrollment/{courseId}/{studCourseId}")
	public String deleteEnrollment(@PathVariable("studCourseId") String studCourseId, @PathVariable("courseId") String courseId) {
		studCourseServ.removeStudentCourseById(studCourseId);
		return "redirect:/admin/view-student-courses/"+courseId;
	}
	
	//ENROLLMENT METHODS ENDS HERE
	
	
	
	
	
//	@PostMapping("/assign-lecturer")
//	public String assignLecturer(@ModelAttribute("Course") Course course, Lecturer lecturer) {
//		lecserv.assignLecturerToCourse(lecturer, course.getId());
//		
//		return "redirect:/admin/manage-course";
//	}
//	
//	@GetMapping("/unassign-lecturer")
//	public String unassignLecturerFromCourse(@ModelAttribute("Course") Course course, Lecturer lecturer) {
//		
//		lecserv.unassignLecturerFromCourse(course.getId(),lecturer.getId());
//		
//		return "redirect:/admin/manage-course";
//	}
	
	
	
	
}
