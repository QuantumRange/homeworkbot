package de.quantumrange.homeworkbot.models;

import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "user")
@Entity(name = "purchase")
public class User implements UserDetails {

	private static final Logger logger = LoggerFactory.getLogger(User.class);
	private static final String SEQUENCE_NAME = "sequence_user";

	@Id
	@SequenceGenerator(name = SEQUENCE_NAME,
					sequenceName = SEQUENCE_NAME,
					allocationSize = 1)
	@GeneratedValue(generator = SEQUENCE_NAME, strategy = SEQUENCE)
	private long id;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}
