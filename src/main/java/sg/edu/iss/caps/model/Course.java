package sg.edu.iss.caps.model;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Course {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	private String courseName;
	private String courseDescription;
	private int maxSize;
	//private Date startDate;
	//private Date endDate;
	
	@ManyToMany (mappedBy= "courses")
	private List<Lecturer> lecturers;
	
	@OneToMany(mappedBy="course")
	private List<StudentCourse> studentCourses; 
}
