package sg.edu.iss.caps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Student;
import sg.edu.iss.caps.model.StudentCourse;
import sg.edu.iss.caps.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studrepo;
	
	@Override
	public List<Student> getAllStudents() {
		return studrepo.findAll();
	}

	@Override
	public void saveStudent(Student student) {
		this.studrepo.save(student);
	}

	@Override
	public Student getStudentById(String id) {
		Optional<Student> optional = studrepo.findById(id);
		Student student = null;

		if(optional.isPresent()){
			student = optional.get();
		} else {
			throw new RuntimeException("Student not found.");
		}

		return student;
	
	}

	@Override
	public void deleteStudentById(String id) {
		this.studrepo.deleteById(id);

	}

	@Override
	public List<Student> returnStudentByName(String name) {
		return null;
	}

	@Override
	public List<StudentCourse> findStudCoursesByStudId(String id) {
		return getStudentById(id).getStudentCourses();
	}
	@Override
	public List<Student> returnStudentByCredentials(String username, String password) {
		// TODO Auto-generated method stub
		return studrepo.searchStudentByCredentials(username, password);
	}

}
