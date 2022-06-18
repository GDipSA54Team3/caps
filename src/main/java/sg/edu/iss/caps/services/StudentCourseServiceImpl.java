package sg.edu.iss.caps.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.StudentCourse;
import sg.edu.iss.caps.repositories.StudentCourseRepository;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

	@Autowired
	private StudentCourseRepository scr;
	
	@Override
	public void newStudentCourse(StudentCourse studentCourse) {
		scr.saveAndFlush(studentCourse);
	}

//	@Override
//	public List<StudentCourse> getStudentCourseByStudId(String id) {
//		return scr.findStudentCourseByStudId(id);
//		
//	}

}
