package sg.edu.iss.caps.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Lecturer;

@Service
public interface LecturerService {
	
		List<Lecturer> getAllLecturer();
		
		void saveLecturer(Lecturer lecturer);
		
		Lecturer getLecturerById(String id);
		
		
		void deleteLecturerById(String id);
		
	
		List<Lecturer> returnLecturerByName(String name);
		
		//need to add grade//

}
