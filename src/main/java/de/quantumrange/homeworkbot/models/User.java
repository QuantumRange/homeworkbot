package de.quantumrange.homeworkbot.models;

import de.quantumrange.homeworkbot.security.UserPermission;
import de.quantumrange.homeworkbot.security.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.apache.tomcat.jni.Address;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "user")
@Entity(name = "user")
public class User implements UserDetails {

	private static final Logger logger = LoggerFactory.getLogger(User.class);
	private static final String SEQUENCE_NAME = "sequence_user";

	@Id
	@SequenceGenerator(name = SEQUENCE_NAME,
					sequenceName = SEQUENCE_NAME,
					allocationSize = 1)
	@GeneratedValue(generator = SEQUENCE_NAME, strategy = SEQUENCE)
	private long id;
	private @Column(name = "id", unique = true, updatable = false) String username;
	private @Column(nullable = false) long discordID;
	private @Column(nullable = false, columnDefinition = "TEXT") String password;

	@ToString.Exclude
	@ManyToOne(targetEntity = School.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private @NotNull School school;

	private @Column(nullable = false) UserRole role;
	private @Column(nullable = false) long permissions;

	private @Column(nullable = false) boolean isAccountNonExpired;
	private @Column(nullable = false) boolean isAccountNonLocked;
	private @Column(nullable = false) boolean isCredentialsNonExpired;
	private @Column(nullable = false) boolean isEnabled;

	private transient Collection<? extends GrantedAuthority> grantedAuthorities;

	public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
		if (grantedAuthorities == null) {
			grantedAuthorities = UserPermission.toGrantedAuthorities(permissions, role);
		}

		return grantedAuthorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getGrantedAuthorities();
	}

	public void setRole(UserRole role) {
		this.role = role;
		this.permissions = UserPermission.toData(role.getPermissions());

		grantedAuthorities = null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return getId() == user.getId() && getDiscordID() == user.getDiscordID() && Objects.equals(getUsername(), user.getUsername());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getUsername(), getDiscordID());
	}
}
