package sg.edu.iss.caps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.iss.caps.model.*;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, String> {

//	@Query("SELECT s FROM StudentCourse s JOIN s.student c WHERE c.id = :id")
//	List<StudentCourse> findStudentCourseByStudId(String id);
}
