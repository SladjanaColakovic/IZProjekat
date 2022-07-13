package com.projekat.inzinjering.dto;

public class SimilarityDTO {
	private String processorBrand;
	private String processorName;
	private int cores;
	private double processorFrequency;
	private String ram;
	private int ramCapacity;
	private String hardDisc;
	private int hardDiscCapacity;
	private String graphicsType;
	private String graphicsCard;
	private String chipset;
	
	
	public SimilarityDTO(String processorBrand, String processorName, int cores, double processorFrequency, String ram,
			int ramCapacity, String hardDisc, int hardDiscCapacity, String graphicsType, String graphicsCard,
			String chipset) {
		super();
		this.processorBrand = processorBrand;
		this.processorName = processorName;
		this.cores = cores;
		this.processorFrequency = processorFrequency;
		this.ram = ram;
		this.ramCapacity = ramCapacity;
		this.hardDisc = hardDisc;
		this.hardDiscCapacity = hardDiscCapacity;
		this.graphicsType = graphicsType;
		this.graphicsCard = graphicsCard;
		this.chipset = chipset;
	}
	public String getProcessorBrand() {
		return processorBrand;
	}
	public void setProcessorBrand(String processorBrand) {
		this.processorBrand = processorBrand;
	}
	public String getProcessorName() {
		return processorName;
	}
	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	public int getCores() {
		return cores;
	}
	public void setCores(int cores) {
		this.cores = cores;
	}
	public double getProcessorFrequency() {
		return processorFrequency;
	}
	public void setProcessorFrequency(double processorFrequency) {
		this.processorFrequency = processorFrequency;
	}
	public String getRam() {
		return ram;
	}
	public void setRam(String ram) {
		this.ram = ram;
	}
	public int getRamCapacity() {
		return ramCapacity;
	}
	public void setRamCapacity(int ramCapacity) {
		this.ramCapacity = ramCapacity;
	}
	public String getHardDisc() {
		return hardDisc;
	}
	public void setHardDisc(String hardDisc) {
		this.hardDisc = hardDisc;
	}
	public int getHardDiscCapacity() {
		return hardDiscCapacity;
	}
	public void setHardDiscCapacity(int hardDiscCapacity) {
		this.hardDiscCapacity = hardDiscCapacity;
	}
	public String getGraphicsType() {
		return graphicsType;
	}
	public void setGraphicsType(String graphicsType) {
		this.graphicsType = graphicsType;
	}
	public String getGraphicsCard() {
		return graphicsCard;
	}
	public void setGraphicsCard(String graphicsCard) {
		this.graphicsCard = graphicsCard;
	}
	public String getChipset() {
		return chipset;
	}
	public void setChipset(String chipset) {
		this.chipset = chipset;
	}
	
	
}
