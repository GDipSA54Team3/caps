package sg.edu.iss.caps.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.caps.model.Lecturer;
import sg.edu.iss.caps.services.LecturerService;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	/*
	 * REPOSITORIES TO INJECT STARTS HERE
	 */

	@Autowired
	private LecturerService lecserv;
	
	/*
	 * REPOSITORIES TO INJECT ENDS HERE
	 */
	
	
	
	/*
	 * LECTURER METHODS START HERE
	 */
	
	@RequestMapping("/manage-lecturers")
	public String viewHomePage(Model model) {
		model.addAttribute("listLecturers", lecserv.getAllLecturers());
		return "managelecturers";
	}
	
	@GetMapping("/add-new-lecturer")
	public String newLecturer(Model model) {
		Lecturer lecturer = new Lecturer();
		
		model.addAttribute("lecturer", lecturer);
		
		return "newLecturer";
	}
	
	@PostMapping("/save-lecturer")
	public String saveLecturer(@ModelAttribute("lecturer") Lecturer lecturer) {
		lecserv.saveLecturer(lecturer);
		
		return "redirect:/admin/manage-lecturers";
	}
	

	@GetMapping("/update-lecturer/{id}")
	public String updateLecturer(@PathVariable(value = "id") String id, Model model) {
		
		Lecturer lecturer = lecserv.getLecturerById(id);
		
		System.out.println("This code works");
		
		model.addAttribute("lecturer", lecturer);
		
		return "updateLecturer";
	}
	
	@GetMapping("/delete-lecturer/{id}")
	public String deleteLecturer(@PathVariable(value = "id") String id, Model model) {
		
		lecserv.deleteLecturerById(id);
		
		return "redirect:/admin/manage-lecturers";
	}
	
	@PostMapping("/search-lecturers")
	public String searchLecturers(@Param("name") String name, Model model) {
		List<Lecturer> listLecturers = lecserv.returnLecturerByName(name);
		
		model.addAttribute("listLecturers", listLecturers);
		
		return "managelecturers";
	}
	
	/*
	*/
	
	/*
	 * LECTURER METHODS END HERE
	 */
}
