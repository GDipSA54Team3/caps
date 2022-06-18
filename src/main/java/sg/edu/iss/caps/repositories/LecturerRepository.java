package sg.edu.iss.caps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.iss.caps.model.*;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, String> {

	@Query("select l from Lecturer l where l.firstName like %?1% or l.lastName like %?1%")
	List<Lecturer> searchLecturerByName(String name);
}
