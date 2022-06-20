package sg.edu.iss.caps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.iss.caps.model.*;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, String> {

}
