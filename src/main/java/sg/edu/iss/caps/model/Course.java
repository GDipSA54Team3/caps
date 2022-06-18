package sg.edu.iss.caps.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

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
	
	@Column(nullable = false)
	private String courseName;
	
	@Column(nullable = false, length = 1000)
	private String courseDescription;
	
	@Column(nullable = false)
	private int maxSize;
	//private Date startDate;
	//private Date endDate;
	 	 
	@ManyToMany (cascade = CascadeType.ALL)
	@JoinTable(name="lecturer_course",
	joinColumns= @JoinColumn(name= "course_Id"),
	inverseJoinColumns= @JoinColumn(name="lecturer_Id"))
	private List<Lecturer> lecturers;
	
	@OneToMany(mappedBy="course", cascade = CascadeType.ALL)
	private List<StudentCourse> studentCourses;

	public Course(String courseName, String courseDescription, int maxSize) {
		super();
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.maxSize = maxSize;
	} 
	
}
