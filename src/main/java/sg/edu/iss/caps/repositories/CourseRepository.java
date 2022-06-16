package sg.edu.iss.caps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.caps.model.*;


public interface CourseRepository extends JpaRepository<Course, String> {
	
	
	@Query("select c from Course c where c.courseName like %?1%")
	List<Course> searchCourseByName(String name);

}
