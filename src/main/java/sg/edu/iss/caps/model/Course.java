package sg.edu.iss.caps.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Course {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	@Column(nullable = false)
	private String courseName;
	
	@Column(nullable = false, length = 1000)
	private String courseDescription;
	
	@Column(nullable = false)
	private int maxSize;
	//private Date startDate;
	//private Date endDate;
	
	@ManyToMany (mappedBy= "courses")
	private List<Lecturer> lecturers;
	
	@OneToMany(mappedBy="course")
	private List<StudentCourse> studentCourses;

	public Course(String courseName, String courseDescription, int maxSize) {
		super();
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.maxSize = maxSize;
	} 
	
	
}
