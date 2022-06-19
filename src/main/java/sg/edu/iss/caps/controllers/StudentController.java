package sg.edu.iss.caps.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.exceptions.DuplicateException;
import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.CourseStatus;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.StudentCourse;
import sg.edu.iss.caps.services.CourseService;
import sg.edu.iss.caps.services.StudentCourseService;
import sg.edu.iss.caps.services.StudentService;
import sg.edu.iss.caps.utilities.SortByCourseName;
import sg.edu.iss.caps.utilities.SortByStudCourseName;
import sg.edu.iss.caps.utilities.SortByStudentName;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private CourseService courseServ;

	@Autowired
	private StudentCourseService studCourseServ;

	@Autowired
	private StudentService studServ;

	@GetMapping("/main")
	public String mainPage() {
		return "studenthome";
	}

	@GetMapping("/selectStudentForReg")
	public String selectStudentForReg(Model model) {
		List<Student> studList = studServ.getAllStudents();
		Collections.sort(studList, new SortByStudentName());
		model.addAttribute("studentList", studList);
		return "studentcourselist";
	}

	@PostMapping("/courseList")
	public String showCourseList(Model model, @Param("id") String id) {
		List<Student> studList = studServ.getAllStudents();
		Collections.sort(studList, new SortByStudentName());
		model.addAttribute("studentList", studList);
		List<Course> courseList = courseServ.getAllCourse();
		Collections.sort(courseList, new SortByCourseName());
		model.addAttribute("listCourses", courseList);
		model.addAttribute("selectedStudent", studServ.getStudentById(id));
		model.addAttribute("studRegisteredCourses", courseServ.findCoursesByStudId(id));
		return "studentcourselist";
	}

//	@PostMapping("/searchCourseList")
//	public String searchCourse(Model model, @Param("search") String search) {
//		model.addAttribute("listCourses", cs.getAllCourse());
//		model.addAttribute("listCourses", cs.findCoursesByName(search));
//		return "studentcourselist";
//	}

	@GetMapping("/registerCourse/{studentId}/{courseId}")
	public String regCourse(Model model, @PathVariable("courseId") String courseId, @PathVariable("studentId") String studentId) throws DuplicateException {
		List<Course> studentRegCourses = courseServ.findCoursesByStudId(studentId);
		if (!studentRegCourses.contains(courseServ.getCourseById(courseId))) {
			StudentCourse test = new StudentCourse(studServ.getStudentById(studentId), courseServ.getCourseById(courseId), CourseStatus.ENROLLED);
			studCourseServ.newStudentCourse(test);
		} else {
			throw new DuplicateException(String.format("\n\n\n ErrorRegistrationFailed: Student is already enrolled in \"%s\" \n\n", courseServ.getCourseById(courseId).getCourseName()));
		}
		return "redirect:/student/selectStudentForReg";
	}

	@GetMapping("/findMyCourses")
	public String myCourses(Model model) {
		List<Student> studList = studServ.getAllStudents();
		Collections.sort(studList, new SortByStudentName());
		model.addAttribute("studentList", studList);;
		return "studentcourses";
	}

	@PostMapping("/myCourses")
	public String myCourses(@Param("id") String id, Model model) {
		List<Student> studList = studServ.getAllStudents();
		Collections.sort(studList, new SortByStudentName());
		model.addAttribute("studentList", studList);
		List<StudentCourse> studCourseList = studServ.findStudCoursesByStudId(id);
		Collections.sort(studCourseList, new SortByStudCourseName());
		model.addAttribute("studentCourses", studCourseList);
		model.addAttribute("selectedStudent", studServ.getStudentById(id));
		return "studentcourses";
	}
}
