package sg.edu.iss.caps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.StudentCourse;
import sg.edu.iss.caps.repositories.StudentCourseRepository;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

	@Autowired
	private StudentCourseRepository studCourseRepo;
	
	@Override
	public void newStudentCourse(StudentCourse studentCourse) {
		studCourseRepo.saveAndFlush(studentCourse);
	}

	@Override
	public void removeStudentCourseById(String id) {
		studCourseRepo.deleteById(id);
	}

}
