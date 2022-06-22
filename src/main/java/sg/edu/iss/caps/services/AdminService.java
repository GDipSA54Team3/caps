package sg.edu.iss.caps.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Admin;


public interface AdminService {
	
	List<Admin> getAllAdmin();
		
	Admin getAdminById(String id);

	List<Admin> returnAdminByCredentials(String username, String password);
}
