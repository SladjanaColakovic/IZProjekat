package com.projekat.inzinjering.dto;

public class MouseDTO {
	private String name;
	private String acceleration;
	private String backlight;
	private String response;
	private String minResolution;
	private String maxResolution;
	private String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(String acceleration) {
		this.acceleration = acceleration;
	}
	public String getBacklight() {
		return backlight;
	}
	public void setBacklight(String backlight) {
		this.backlight = backlight;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getMinResolution() {
		return minResolution;
	}
	public void setMinResolution(String minResolution) {
		this.minResolution = minResolution;
	}
	public String getMaxResolution() {
		return maxResolution;
	}
	public void setMaxResolution(String maxResolution) {
		this.maxResolution = maxResolution;
	}
	
	
		
}
