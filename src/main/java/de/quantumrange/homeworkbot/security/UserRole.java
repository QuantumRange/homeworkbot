package de.quantumrange.homeworkbot.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static de.quantumrange.homeworkbot.security.UserPermission.*;

public enum UserRole {

	CONSUMER	("Kunde",
			Sets.newHashSet(HOMEWORK_CREATE, HOMEWORK_READ, HOMEWORK_MODIFY)),
	ADMIN		("Administrator",
			Sets.newHashSet(SCHOOL_MODIFY, SCHOOL_CREATE, SCHOOL_READ, HOMEWORK_MODIFY, HOMEWORK_READ,
					HOMEWORK_CREATE, HOMEWORK_ASSIGN)),
	DEVELOPER	("Entwickler",
			Sets.newHashSet(UserPermission.values()));

	private final Set<UserPermission> permissions;
	private final String name;

	UserRole(String name, Set<UserPermission> permissions) {
		this.name = name;
		this.permissions = permissions;
	}

	public String getName() {
		return name;
	}

	public Set<UserPermission> getPermissions() {
		return permissions;
	}

	public static UserRole getUserByGrantedAuthority(GrantedAuthority authority) {
		for (UserRole role : values()) {
			if (authority.getAuthority().equals("ROLE_" + role.name())) return role;
		}

		return null;
	}

	public SimpleGrantedAuthority getGrantedAuthority() {
		return new SimpleGrantedAuthority("ROLE_" + this.name());
	}
}
