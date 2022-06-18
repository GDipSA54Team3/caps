package sg.edu.iss.caps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.caps.model.*;


public interface StudentRepository extends JpaRepository<Student, String> {

	@Query("select s from Student s where s.firstName like %?1% or s.lastName like %?1%")
	List<Student> searchStudentByName(String name);
	
	@Query("SELECT c.course FROM Student s JOIN s.studentCourses c WHERE s.id = :id")
	List<Course> findCoursesByStudId(String id);
}
