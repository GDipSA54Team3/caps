
package sg.edu.iss.caps.services;

import java.util.List;

import sg.edu.iss.caps.model.Course;

public interface CourseService {
	
		List<Course> getAllCourse();
		
		void saveCourse(Course course);
		
		
		Course getCourseById(String id);
		
		
		void deleteCourseById(String id);
		
	
		List<Course >returnCourseByName(String name);

		//List<Course> getAllCourses();
		
		List<Course> getCoursesByStudId(String id);

}
