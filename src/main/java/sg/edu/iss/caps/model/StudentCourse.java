package sg.edu.iss.caps.model;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.*;

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
