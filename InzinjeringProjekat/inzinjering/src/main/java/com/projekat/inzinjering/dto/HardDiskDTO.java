package com.projekat.inzinjering.dto;

public class HardDiskDTO {

	private String format;
	private String hdd_interface;
	private String capacity;
	private String speed;
	private boolean hasNvme;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getHdd_interface() {
		return hdd_interface;
	}
	public void setHdd_interface(String hdd_interface) {
		this.hdd_interface = hdd_interface;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public boolean isHasNvme() {
		return hasNvme;
	}
	public void setHasNvme(boolean hasNvme) {
		this.hasNvme = hasNvme;
	}
	
	
}
