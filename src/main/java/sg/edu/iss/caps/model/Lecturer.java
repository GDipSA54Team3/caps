package sg.edu.iss.caps.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Lecturer {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	
	@ManyToMany
	@JoinTable(name="lecturer_course",
		joinColumns= @JoinColumn(name= "lecturer_Id"),
		inverseJoinColumns= @JoinColumn(name="course_Id"))
	private List<Course> courses;

	public Lecturer(String username, String password, String firstName, String lastName, String email) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	
}