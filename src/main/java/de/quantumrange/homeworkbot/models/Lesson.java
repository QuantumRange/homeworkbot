package de.quantumrange.homeworkbot.models;

import java.util.List;

public class Lesson {

	private long id;
	private String code, activityType;
	private List<Teacher> teachers;
	private List<User> participants;
	private List<Room> rooms;

}
