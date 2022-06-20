package sg.edu.iss.caps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.iss.caps.model.*;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
	
	
	@Query("select c from Course c where c.courseName like %?1%")
	List<Course> searchCourseByName(String name);
	
	@Query("select*from Course c where 1=1")
	List<Course> manageCourseByName();
	
	//@Query("Update Course set name='Update Name', description='Update description Value', maxSize='Update max size value' where id='course_id'")
	
	
	//@Query("Delete from Course where id='course_id'")
}
