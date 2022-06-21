package sg.edu.iss.caps.controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.iss.caps.model.AppPage;
import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.LoginUser;
import sg.edu.iss.caps.model.Role;
import sg.edu.iss.caps.services.CourseService;
import sg.edu.iss.caps.services.LecturerService;
import sg.edu.iss.caps.services.StudentService;


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
	private StudentService studserv;
	/*
	 * REPOSITORIES TO INJECT ENDS HERE
	 */
	
	@RequestMapping("/home")
	public String openHomePage (Model model) {
		model.addAttribute("lecCount", lecserv.getAllLecturers().size());
		model.addAttribute("courseCount", couserv.getAllCourse().size());
		model.addAttribute("studCount", studserv.getAllStudents().size());
		
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		loginCon.checkCurrentPage(model, AppPage.ADMIN_HOME);
		return "adminhome";
	}
	
	/*
	 * LECTURER METHODS START HERE
	 */
	
	@RequestMapping("/manage-lecturers")
	public String viewManageLecturers(Model model) {
		model.addAttribute("listLecturers", lecserv.getAllLecturers());
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		loginCon.checkCurrentPage(model, AppPage.ADMIN_MANAGE_LECTURERS);
		return "managelecturers";
	}
	
	@GetMapping("/add-new-lecturer")
	public String newLecturer(Model model) {
		Lecturer lecturer = new Lecturer();
		
		model.addAttribute("lecturer", lecturer);
		
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		loginCon.checkCurrentPage(model, AppPage.ADMIN_MANAGE_LECTURERS);
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
		loginCon.checkCurrentPage(model, AppPage.ADMIN_MANAGE_LECTURERS);
		return "updateLecturer";
	}
	
	@GetMapping("/delete-lecturer/{id}")
	public String deleteLecturer(@PathVariable(value = "id") String id, Model model) {
		
		lecserv.deleteLecturerById(id);
		
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		loginCon.checkCurrentPage(model, AppPage.ADMIN_MANAGE_LECTURERS);
		return "redirect:/admin/manage-lecturers";
	}
	
	@PostMapping("/search-lecturers")
	public String searchLecturers(@Param("name") String name, Model model) {
		List<Lecturer> listLecturers = lecserv.returnLecturerByName(name);
		
		model.addAttribute("listLecturers", listLecturers);
		
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		loginCon.checkCurrentPage(model, AppPage.ADMIN_MANAGE_LECTURERS);
		return "managelecturers";
	}
	
	@RequestMapping("/view-lecturer-courses/{id}")
	public String viewLecturerCourses(@PathVariable(value = "id") String id, Model model) {
		
		Lecturer lecturer = lecserv.getLecturerById(id);
		
		List<Course> listCourse = lecturer.getCourses();
		
		model.addAttribute("listCourse", listCourse);
		model.addAttribute("lecturer", lecturer);
		
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		loginCon.checkCurrentPage(model, AppPage.ADMIN_MANAGE_LECTURERS);
		return "viewLecturerCourses";
	}
	
	
	@GetMapping("/save-lecturer-to-course/{lec-id}/{course-id}")
	public String saveLecturerToCourse(@PathVariable(value = "lec-id") String lecId, 
			@PathVariable(value = "course-id") String courseId) {
		
		Lecturer lec = lecserv.getLecturerById(lecId);
		Course course = couserv.getCourseById(courseId);
		
		lec.getCourses().add(course);
		lecserv.saveLecturer(lec);
		
		return "redirect:/admin/view-lecturer-courses/" + lec.getId();
	}
	
	@GetMapping("/delete-course-from-lecturer/{lec-id}/{course-id}")
	public String removeCourseFromLecturer(@PathVariable(value = "lec-id") String lecId, 
			@PathVariable(value = "course-id") String courseId) {
		
		Lecturer lec = lecserv.getLecturerById(lecId);
		Course course = couserv.getCourseById(courseId);
		
		lec.getCourses().remove(course);
		lecserv.saveLecturer(lec);
		
		return "redirect:/admin/view-lecturer-courses/" + lec.getId();
	}
	
	
	
	@GetMapping("/add-course-to-lecturer/{id}")
	public String addCourseToLecturer(Model model, 
			@PathVariable(value = "id") String lecId) {
		
		Lecturer lecturer = lecserv.getLecturerById(lecId);
		
		List<Course> allCourses = couserv.getAllCourse();
		
		List<Course> listLecCourse = allCourses
			.stream()
			.filter(x -> x.getLecturers().contains(lecturer))
			.collect(Collectors.toList());
		
		
		model.addAttribute("allCourses", allCourses);
		model.addAttribute("listLecCourse", listLecCourse);
		model.addAttribute("lecturer", lecturer);
		
		loginCon.setAdminRole(model, new LoginUser(Role.ADMIN));
		loginCon.checkCurrentPage(model, AppPage.ADMIN_MANAGE_LECTURERS);
		return "addCourseToLecturer";
	}
	
	/*
	 * @GetMapping("/manage-lecturers/page/{pageNo}") public String
	 * findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) { int
	 * pageSize = 10;
	 * 
	 * Page<Lecturer> page = lecserv.findPaginated(pageNo, pageSize);
	 * 
	 * List<Lecturer> listLecturers = page.getContent();
	 * 
	 * model.addAttribute("currentPage",pageNo); model.addAttribute("totalPages",
	 * page.getTotalPages()); model.addAttribute("totalItems",
	 * page.getTotalElements()); model.addAttribute("listLecturers", listLecturers);
	 * loginCon.checkCurrentPage(model, AppPage.ADMIN_MANAGE_LECTURERS);
	 * 
	 * return "managelecturers"; }
	 * 
	 * @GetMapping("/search-lecturers/page/{pageNo}") public String
	 * findPaginatedSearch(@PathVariable(value = "pageNo") int pageNo, Model model)
	 * { int pageSize = 10;
	 * 
	 * Page<Lecturer> page = lecserv.findPaginated(pageNo, pageSize);
	 * 
	 * List<Lecturer> listLecturers = page.getContent();
	 * 
	 * model.addAttribute("currentPage",pageNo); model.addAttribute("totalPages",
	 * page.getTotalPages()); model.addAttribute("totalItems",
	 * page.getTotalElements()); model.addAttribute("listLecturers", listLecturers);
	 * loginCon.checkCurrentPage(model, AppPage.ADMIN_MANAGE_LECTURERS);
	 * 
	 * return "managelecturers"; }
	 */
	
	/*
	 * LECTURER METHODS END HERE
	 */
	@RequestMapping("/manage-course")
	public String viewCoursePage(Model model) {
		model.addAttribute("listCourse", couserv.getAllCourse());
		return "manageCourse";
	}
	@PostMapping("/save-course")
	public String saveCourse(@ModelAttribute("Course") Course course) {
		couserv.saveCourse(course);
		
		return "redirect:/admin/manage-course";
	}
	
	@GetMapping("/edit-course/{id}")
	public String editCourseById(@PathVariable(value = "id") String id, Model model) {
		
		Course course = couserv.getCourseById(id);
		
		
		model.addAttribute("Course", course);
		
		return "updateCourse";
	}
	@GetMapping("/delete-course/{id}")
	public String removeCourse(@PathVariable(value = "id") String id, Model model) {
		
		couserv.deleteCourseById(id);
		
		return "redirect:/admin/manage-course";
	}

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
	
	
	//
	// MANAGE STUDENTS STARTS HERE
	//



	@RequestMapping("/manage-students")
	public String viewStudentPage(Model model) {
		model.addAttribute("listStudents", studserv.getAllStudents());
		return "managestudents";
	}



	/*
	 * @PostMapping("/saveStudent") public String
	 * saveEmployee(@ModelAttribute("student")Student student) {
	 * studserv.saveStudent(student);
	 * 
	 * //redirect: -> sends user to another page (in this case, the home page)
	 * return "/"; }
	 * 
	 * 
	 * @GetMapping("/updateStudentForm/{id}") public String
	 * updateStudent(@PathVariable(value = "id") String id, Model model) {
	 * 
	 * 
	 * Student student = studserv.getStudentById(id);
	 * 
	 * 
	 * 
	 * model.addAttribute("student", student);
	 * 
	 * return "updateStudent"; }
	 * 
	 * 
	 * 
	 * 
	 * @GetMapping("/deleteStudent/{id}") public String
	 * deleteStudent/(@PathVariable(value = "id") String id, Model model) {
	 * 
	 * studserv.deleteStudentById(id);
	 * 
	 * return ""; }
	 * 
	 * public String searchStudent(@Param("name") String name, Model model) {
	 * 
	 * 
	 * List<Student> listStudents = studserv.returnStudentByName(name);
	 * 
	 * 
	 * model.addAttribute("listStudents", listStudents);
	 * 
	 * 
	 * return "index"; }
	 */
}
