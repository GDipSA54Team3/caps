package sg.edu.iss.caps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.iss.caps.model.*;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

	@Query("select s from Student s where s.firstName like %?1% or s.lastName like %?1%")
	List<Student> searchStudentByName(String name);
	
	@Query("SELECT c FROM Student s JOIN s.studentCourses c WHERE c.student.id = :id")
	List<StudentCourse> findStudCoursesByStudId(String id);
	
	@Query("SELECT c.course FROM Student s JOIN s.studentCourses c WHERE c.student.id = :id")
	List<Course> findCoursesByStudId(String id);
	
	@Query("select s from Student s where s.username = ?1 and s.password = ?2")
	List<Student> searchStudentByCredentials(String username, String password);
}
