package sg.edu.iss.caps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.caps.model.*;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, String> {

}
