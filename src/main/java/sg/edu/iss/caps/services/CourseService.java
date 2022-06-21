
package sg.edu.iss.caps.services;

import java.util.List;

import sg.edu.iss.caps.model.Course;

public interface CourseService {

		List<Course> getCoursesByLecturerId(String lecturerId); 
		
		List<Course> findByLecturerAndCourse(String lecturerId, String name );
		

	void saveCourse(Course course);

	Course getCourseById(String id);

	void deleteCourseById(String id);

	List<Course> findCoursesByName(String name);

	List<Course> findCoursesByStudId(String id);

    List<Course> getCoursesByLecturerId(String lecturerId);
    
    int getEnrollCountByCourseId(String id);
    
    boolean isCapacityOk(String courseId);
}
