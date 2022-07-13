package model;

import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

public class CaseDescription implements CaseComponent {

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
	
	
	
	@Override
	public String toString() {
		return "processorBrand=" + processorBrand + ",processorName=" + processorName + ",cores="
				+ cores + ",processorFrequency=" + processorFrequency + ",ram=" + ram + ",ramCapacity=" + ramCapacity
				+ ",hardDisc=" + hardDisc + ",hardDiscCapacity=" + hardDiscCapacity + ",graphicsType=" + graphicsType
				+ ",graphicsCard=" + graphicsCard + ",chipset=" + chipset;
	}



	public String getGraphicsType() {
		return graphicsType;
	}



	public void setGraphicsType(String graphicsType) {
		this.graphicsType = graphicsType;
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



	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id",this.getClass());
	}
}
