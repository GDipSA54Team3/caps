package sg.edu.iss.caps.controllers;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Grade;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.LoginBag;
import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.StudentCourse;
import sg.edu.iss.caps.repositories.CourseRepository;
import sg.edu.iss.caps.repositories.StudentRepository;
import sg.edu.iss.caps.services.CourseService;
import sg.edu.iss.caps.services.StudentService;
import sg.edu.iss.caps.utilities.SortByCourseName;
import sg.edu.iss.caps.utilities.SortByStudCourseName;
import sg.edu.iss.caps.utilities.SortByStudentName;


@Controller
@RequestMapping("/lecturer")
@SessionAttributes("loggeduser")
public class LecturerController {


	@Autowired
	private CourseService corserv;
	
	@Autowired
	private StudentService stuserv;
	

	//View all courses that a lecturer teach
	@RequestMapping("/view-courses")
	public String viewLecCourses(
			Model model, HttpSession session	) {
		
		String userId  = getCurrentUserId(session);
		
		//To get the courses of currently logged in lecturer
		List<Course> courses = corserv.getCoursesByLecturerId(userId);
		Collections.sort(courses, new SortByCourseName());
		//Addcourses to listCourses for display
		model.addAttribute("listCourses",  courses);
																							
		return "leccourses";		
	}	
	
	//Search a course from the list courses a lecturer teach
	@PostMapping("/search-courses")
	public String searchCourses(@Param("name") String name, Model model,
			HttpSession session) {
		
		String userid  = getCurrentUserId(session);
		//find courses by Course name and lecturer if searched with non empty string
		if (name != "") {
			List<Course> courses = corserv.findByLecturerAndCourse(userid, name);		
			Collections.sort(courses, new SortByCourseName());
		model.addAttribute("listCourses", courses);
		
		return "leccourses";
		}
		//list all courses for the lecturer if searched with empty string
		else 
			return "redirect:/lecturer/view-courses";
	}
	
	//Returns the userId of the currently logged in user
	public String getCurrentUserId (HttpSession session){
		
		LoginBag lb =(LoginBag) session.getAttribute("loggeduser");
		String userId = lb.getLoggeduser().getUserId();
		
		return userId;
		
	}
	
	//View students currently taking a course
	@RequestMapping("/view-course-student-list/{id}")
	public String viewCourseStudentView(@PathVariable(value = "id") String courseId, Model model) {
		
		List<Student> students = stuserv.getCurrentStudentsByCourse(courseId);
		Collections.sort(students, new SortByStudentName());
		Course curCourse = corserv.getCourseById(courseId);
		model.addAttribute("curCourse", curCourse);		
		model.addAttribute("listStudents", students);	
			
		return "viewcoursestudent"; 
	}
	
	//Searching students currently taking a course
	@PostMapping("/search-students/{id}")
	public String searchStudents(@PathVariable(value = "id") String courseId, @Param("name") String name, Model model,
			HttpSession session) {
		
		String userid  = getCurrentUserId(session);
		//if searched with non empty string
		if (name != "") {
						
			List<Student> students = stuserv.searchCourseStudentByName(courseId, name);		
			Course curCourse = corserv.getCourseById(courseId);			
			model.addAttribute("curCourse", curCourse);	
			Collections.sort(students, new SortByStudentName());
		    model.addAttribute("listStudents", students);
		
		return "viewcoursestudent";
		}
		else 
			return "redirect:/lecturer/view-course-student-list/"+courseId;
	}
	
	//View student perfomance 
	@RequestMapping("/view-perfomance/{id}")
	public String viewStudentPerfomance(@PathVariable(value = "id") String studentId, Model model) {
				
		List<StudentCourse> studCourseList = stuserv.findStudCoursesByStudId(studentId);
		
		if (!studCourseList.isEmpty()) {
		double gpa = calculateGpa(studentId, studCourseList);		
		model.addAttribute("studentCourses", studCourseList);
		 model.addAttribute("gpa", gpa);		  
		return "viewstudentprofile";
		}
		else {			
			Student student = stuserv.getStudentById(studentId);
			model.addAttribute("student", student);
		}
		return "nostudentcourses";	
		
	}
	
	//Search a student to view perfomance
	@RequestMapping("/search-students")	
	public String searchAllStudents() {				  
		return "searchstudents"; 		
	}
	
	//Search results
	@RequestMapping("/search-results")		
	public String showStudentSearchResults(@Param(value= "name") String name, Model model) {		
		List<Student> students = stuserv.returnStudentByName(name);		
		Collections.sort(students, new SortByStudentName());
	    model.addAttribute("listStudents", students);		
		return "searchstudentresults"; 		
		
	}	
	//Calcutaing current GPA for a student
	public Double calculateGpa (String studentId, List<StudentCourse> studCourseList) {
		
		double gpa = 0.0;
		double numerator = 0.0;
		double denominator = 0.0;
		double gradePointMax = 5;
			for (StudentCourse studCor : studCourseList) {
				double creditUnit  = 5; //corserv.getCreditUnit(studCor.getCourse().getId());				
				double gradePoint = 0;
				
				switch (studCor.getGrade()){					
				case A:
					gradePoint = 5;
					break;
				case B:
					gradePoint = 4;
					break;
				case C:
					gradePoint = 3;
					break;
				case D:
					gradePoint = 2;
					break;
				case F:
					gradePoint = 0;
					break;
				case NA:
					gradePoint = 0;
					break;			
				}				
				numerator += creditUnit*gradePoint;				
				if (studCor.getGrade() != Grade.NA) {					
					denominator += creditUnit*gradePointMax;
				}
				else {
					denominator += 0;
				}
			}			
			gpa = (numerator/denominator)*gradePointMax; 
			return gpa;
	}
}
