package sg.edu.iss.caps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.caps.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

}
