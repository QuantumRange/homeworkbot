package de.quantumrange.homeworkbot.models;

import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "purchase")
@Entity(name = "purchase")
public class School {

	private static final String SEQUENCE_NAME = "sequence_school";

	private static final Logger logger = LoggerFactory.getLogger(User.class);
	@Id
	@SequenceGenerator(name = SEQUENCE_NAME,
			sequenceName = SEQUENCE_NAME,
			allocationSize = 1)
	@GeneratedValue(generator = SEQUENCE_NAME,
			strategy = GenerationType.SEQUENCE)
	private @Column(name = "id") long id;
	private @Column(name = "token") String unitsToken;

	@OneToMany(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "su_fk", referencedColumnName = "id")
	@ToString.Exclude
	private List<Class> classes;

	@OneToMany(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "se_fk", referencedColumnName = "id")
	@ToString.Exclude
	private List<Exam> exams;

	@OneToMany(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "sh_fk", referencedColumnName = "id")
	@ToString.Exclude
	private List<Homework> homeworks;

	@OneToMany(targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "su_fk", referencedColumnName = "id")
	@ToString.Exclude
	private List<User> users;

}
