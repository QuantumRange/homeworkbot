package de.quantumrange.homeworkbot.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public enum UserRole {

	CONSUMER	("Kunde",
			Sets.newHashSet()),
	MANAGER		("Manager",
			Sets.newHashSet(ORDERS_MODIFY, ORDERS_READ_ALL, USERS_READ, ORDERS_REQUEST_PDF)),
	ADMIN		("Administrator",
			Sets.newHashSet(ORDERS_DELETE, ORDERS_MODIFY, ORDERS_PAY, ORDERS_CREATE, ORDERS_READ_ALL)),
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
