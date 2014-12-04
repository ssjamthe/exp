package data;

import util.TimeHelper;

public class Toy {
	
	
	private String toyId;
	private int arrivalTime;
	private int timeToBuild;
	
	
	public void setValues(String toyId,String arrivalTime,int timeToBuild)
	{
		this.toyId = toyId;
		this.arrivalTime = TimeHelper.parseTime(arrivalTime);
		this.timeToBuild = timeToBuild;
	}
	
	

	public String getToyId() {
		return toyId;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getTimeToBuild() {
		return timeToBuild;
	}

}
