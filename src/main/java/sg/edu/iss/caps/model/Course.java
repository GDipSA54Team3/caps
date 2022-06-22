package sg.edu.iss.caps.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	
	@Column(nullable = false, unique=true)
	private String courseName;
	
	@Column(nullable = false, length = 1000)
	private String courseDescription;
	
	@Column(nullable = false)
	private int creditUnit;
	
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

	public Course(String courseName, String courseDescription, int maxSize, int creditUnit) {
		super();
		this.courseName = courseName;
		this.courseDescription = courseDescription;
		this.maxSize = maxSize;
		this.creditUnit = creditUnit;
	} 
	
}
