package sg.edu.iss.caps.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Admin {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	@Column(nullable = false, length = 12)
	private String username;
	
	@Column(nullable = false, length = 12)
	private String password;
	
	@Column(nullable = false, length = 12)
	private String firstName;
	
	@Column(nullable = false, length = 12)
	private String lastName;
	
	@Column(nullable = false, length = 12)
	private String email;
}
