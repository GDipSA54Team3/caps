
package sg.edu.iss.caps.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.*;

@Service
public interface StudentService {
	// fetch all students in the database
	List<Student> getAllStudents();

	// saving the student object in the database
	void saveStudent(Student student);

	// fetching one student using the id
	Student getStudentById(String id);

	// deleting a student in the database
	void deleteStudentById(String id);

	// returns a list of student that were searched by name
	List<Student> returnStudentByName(String name);

	List<Student> returnStudentByCredentials(String username, String password);

	List<Student> getStudentByCourse(String courseId);

	List<StudentCourse> findStudCoursesByStudId(String id);

}
