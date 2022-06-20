package sg.edu.iss.caps.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Course;
import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.model.Student;

import sg.edu.iss.caps.repositories.CourseRepository;
import sg.edu.iss.caps.repositories.LecturerRepository;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courepo;
	
	@Override
	public List<Course> getAllCourse() {
		// TODO Auto-generated method stub
		return courepo.findAll();
	}

	@Override
	public void saveCourse(Course course) {
		// TODO Auto-generated method stub
		this.courepo.save(course);
	}

	@Override
	public Course getCourseById(String id) {
		// TODO Auto-generated method stub
		Optional<Course> optional = courepo.findById(id);
		Course course= null;

		if(optional.isPresent()){
			course = optional.get();
		} else {
			throw new RuntimeException("Course not found.");
		}

		return course;
	
	}

	@Override
	public void deleteCourseById(String id) {
		// TODO Auto-generated method stub
		this.courepo.deleteById(id);

	}

	@Override
	public List<Course> returnCourseByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public  List<Course> getCoursesByLecturerId(String lecturerId)  {
		
		List<Course> clist = courepo.getCoursesByLecturerId(lecturerId);
		
		return clist;
	 }
		
}
