package de.quantumrange.homeworkbot.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public enum UserPermission {

	/* HOMEWORK 0 -> 9 */
	HOMEWORK_CREATE		(0, "homework:create"),
	HOMEWORK_MODIFY		(1, "homework:modify"),
	HOMEWORK_ATTACH		(2, "homework:attach"),
	HOMEWORK_READ		(3, "homework:read"),
	HOMEWORK_ASSIGN		(4, "homework:assign"),

	/* USERS 10 -> 19 */
	SCHOOL_CREATE		(10, "school:create"),
	SCHOOL_READ			(11, "school:read"),
	SCHOOL_MODIFY		(12, "school:modify"),
	SCHOOL_DELETE		(13, "school:delete");

	static {
		final Logger logger = LoggerFactory.getLogger(UserPermission.class);

		logger.info("Check UserPermission for errors.");

		if (values().length > 64) {
			logger.error("UserPermission.class have to much Permissions. Only 64 are allowed because a long hs 64 bits.");
			System.exit(0);
		}

		List<Long> temp = new ArrayList<>(values().length);

		for (UserPermission value : values()) {
			if (!temp.contains(value.getId())) {
				temp.add(value.getId());
			} else {
				logger.error("The id {} exists more then one times in the Permissions. [{}]",
						value.getId(),
						Arrays.stream(values())
								.filter(u -> u.getId() == value.getId())
								.toList());
				System.exit(0);
			}
		}

		logger.info("UserPermissions is valid.");
	}

	private final long id;
	private final String permission;

	UserPermission(long id, String permission) {
		this.id = id;
		this.permission = permission;
	}

	public long getId() {
		return id;
	}

	public String getPermission() {
		return permission;
	}

	public static Set<UserPermission> of(long data) {
		return Arrays.stream(values())
				.filter(permission -> ((data >> permission.getId()) & 0x1) == 1)
				.collect(Collectors.toCollection(() -> EnumSet.noneOf(UserPermission.class)));
	}

	public static Collection<? extends GrantedAuthority> toGrantedAuthorities(long data, UserRole role) {
		Set<SimpleGrantedAuthority> set = Arrays.stream(values())
				.filter(permission -> ((data >> permission.getId()) & 0x1) == 1)
				.map(UserPermission::getPermission)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
		set.add(role.getGrantedAuthority());
		return set;
	}

	public static long toData(Collection<UserPermission> userPermissions) {
		long perm = 0x0;

		for (UserPermission permission : userPermissions) {
			perm |= 0x1L << permission.getId();
		}

		return perm;
	}

	public boolean hasPermission(long data) {
		return ((data >> getId()) & 0x1) == 1;
	}

}
