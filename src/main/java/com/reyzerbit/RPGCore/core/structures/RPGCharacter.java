package com.reyzerbit.RPGCore.core.structures;

public class RPGCharacter {
	
	private String CharacterID;
	
	private String name;
	private String race;
	private String pClass;
	private int age;
	private int height;
	private String bodytype;
	private String hometown;
	private String description;
	
	public RPGCharacter() {
		
		CharacterID = "";
		
		name = "";
		race = "";
		pClass = "";
		age = 0;
		height = 0;
		bodytype = "";
		hometown = "";
		description = "";
		
	}
	
	public RPGCharacter(String id, String n, String r, String c, int a, int h, String bt, String ht) {
		
		CharacterID = id;
		
		name = n;
		race = r;
		pClass = c;
		age = a;
		height = h;
		bodytype = bt;
		hometown = ht;
		description = "";
		
	}

	public String getCharacterID() {
		return CharacterID;
	}

	public void setCharacterID(String characterID) {
		CharacterID = characterID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getPClass() {
		return pClass;
	}

	public void setPClass(String c) {
		this.pClass = c;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getBodytype() {
		return bodytype;
	}

	public void setBodytype(String bodytype) {
		this.bodytype = bodytype;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
