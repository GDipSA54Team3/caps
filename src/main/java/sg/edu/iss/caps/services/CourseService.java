
package sg.edu.iss.caps.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.*;

@Service
public interface CourseService {
	
		List<Course> getAllCourse();
		
		void saveCourse(Course course);
		
		
		Course getCourseById(String id);
		
		
		void deleteCourseById(String id);
		
	
		List<Course >returnCourseByName(String name);

		List<Course> getCoursesByLecturerId(String lecturerId); 
		

}
