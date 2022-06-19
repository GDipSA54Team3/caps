package sg.edu.iss.caps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.iss.caps.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
	
	@Query("select c from Course c where c.courseName like %:name%")
	List<Course> findCoursesByName(String name);

}
