package sg.edu.iss.caps.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class StudentCourse {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	@ManyToOne
	private Student student;
	
	@ManyToOne
	private Course course;
	
	private String grade;
	//private DateTime enrollmentDate;
	private String courseStatus;
	
	public StudentCourse(Student student, Course course, String grade, String courseStatus) {
		super();
		this.student = student;
		this.course = course;
		this.grade = grade;
		this.courseStatus = courseStatus;
	}
	
	
}
