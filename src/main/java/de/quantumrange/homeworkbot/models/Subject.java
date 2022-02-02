package de.quantumrange.homeworkbot.models;

import java.awt.Color;
import java.util.List;

public class Subject {

	private long id;
	private String name, longName, alternateName;
	private boolean active;
	private Color foregroundColor, backgroundColor;
	private List<Teacher> teachers;
	private List<User> participants;
	private List<Lesson> lessons;

}
