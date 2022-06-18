package com.projekat.inzinjering.dto;

public class ProcessorDTO {
	private String name;
	private String frequency;
	private String boostFrequency;
	private String l1Cache;
	private String l2Cache;
	private String l3Cache;
	private int cores;
	private int threads;
	private String TDP;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getBoostFrequency() {
		return boostFrequency;
	}
	public void setBoostFrequency(String boostFrequency) {
		this.boostFrequency = boostFrequency;
	}
	public String getL1Cache() {
		return l1Cache;
	}
	public void setL1Cache(String l1Cache) {
		this.l1Cache = l1Cache;
	}
	public String getL2Cache() {
		return l2Cache;
	}
	public void setL2Cache(String l2Cache) {
		this.l2Cache = l2Cache;
	}
	public String getL3Cache() {
		return l3Cache;
	}
	public void setL3Cache(String l3Cache) {
		this.l3Cache = l3Cache;
	}
	public int getCores() {
		return cores;
	}
	public void setCores(int cores) {
		this.cores = cores;
	}
	public int getThreads() {
		return threads;
	}
	public void setThreads(int threads) {
		this.threads = threads;
	}
	public String getTDP() {
		return TDP;
	}
	public void setTDP(String tDP) {
		TDP = tDP;
	}
	
	

}
