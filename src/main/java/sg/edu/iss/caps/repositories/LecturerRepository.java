package sg.edu.iss.caps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.iss.caps.model.*;


public interface LecturerRepository extends JpaRepository<Lecturer, String> {

	@Query("select l from Lecturer l where l.firstName like %?1% or l.lastName like %?1%")
	List<Course> searchLecturerByName(String name);
}
