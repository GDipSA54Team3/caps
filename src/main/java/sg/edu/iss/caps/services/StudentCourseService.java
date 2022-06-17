package sg.edu.iss.caps.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.*;
@Service
public interface StudentCourseService {

	
	//fetch student by CourseID
	List<Student>getStudentByCourseID();
	
	//fetch student by CourseStatus
	List<Student>getStudentByCourseStatus();
	
	//fetch all Student in the database by grades
		List<Student> getStudentByGrade();
		
	//fetch all Student in the database by Enrollment date//
		List<Student>getStudentByEnrollmentDate();
		
	//deleting a student in the database by Enrollmentdate//
		//void deleteStudentByEnrollmentDate(Date);
		
	//deleting student by CourseStatus
		void deleteStudentByCourseStatus(;)
		
	

		//fetching one student using the id
		Student getStudentById(String id);
		
		//deleting a  student  in the database
		void deleteStudentById(String id);
		
		//returns a list of student that were searched by name
		List<Student> returnStudentByName(String name);*/dentByName(String name);*/
}
		