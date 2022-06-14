package sg.edu.iss.caps.model;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Student {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	
	@OneToMany(mappedBy="student")
	private List<StudentCourse> studentCourses;

	public Student(String username, String password, String firstName, String lastName, String email) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	} 
}
