package sg.edu.iss.caps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.caps.model.Lecturer;

import sg.edu.iss.caps.repositories.LecturerRepository;

@Service
	public class LecturerServiceImpl<returnLecturerByName> implements LecturerService {
		
		
		@Autowired
		private LecturerRepository lectrepo;
		
		//JPA syntax to find all items in the table
	@Override
		public List<Lecturer> getAllLecturer() {
			//TODO Auto-generated method stub
			return lectrepo.findAll();
		}
		
		
		@Override
		public void saveLecturer(Lecturer lecturer) {
			// TODO Auto-generated method stub
			this.lectrepo.save(lecturer);
		}

		@Override
		public Lecturer getLecturerById(String id) {
			// TODO Auto-generated method stub
			Optional<Lecturer> optional = lectrepo.findById(id);
			Lecturer lecturer = null;

			if(optional.isPresent()){
				lecturer = optional.get();
			} else {
				throw new RuntimeException("Lecturer not found.");
			}

			return lecturer;
		
		}

		@Override
		public void deleteLecturerById(String id) {
			// TODO Auto-generated method stub
			this.lectrepo.deleteById(id);
		}
		
		@Override

		public List<Lecturer> returnLecturerByName(String name) {
			// TODO Auto-generated method stub

			return null;
		}



		


}
