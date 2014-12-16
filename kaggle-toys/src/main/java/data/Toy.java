package data;

import util.TimeHelper;

public class Toy implements Comparable<Toy>{
	
	
	private String toyId;
	private int arrivalTime;
	private int timeToBuild;
	private int idealEndTime;
	private int idealStartTime;
	
	
	public Toy()
	{
		
	}
	
	public void setValues(String toyId,String arrivalTime,int timeToBuild)
	{
		if(toyId == null)
			throw new RuntimeException("toy id is null");
		this.toyId = toyId;
		this.arrivalTime = TimeHelper.parseTime(arrivalTime);
		this.timeToBuild = timeToBuild;
		this.idealStartTime = TimeHelper.getNextStartTime(this.arrivalTime);
		this.idealEndTime = idealStartTime + timeToBuild;
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

	public int getIdealEndTime() {
		return idealEndTime;
	}

	public int getIdealStartTime() {
		return idealStartTime;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((toyId == null) ? 0 : toyId.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Toy other = (Toy) obj;
		if (toyId == null) {
			if (other.toyId != null)
				return false;
		} else if (!toyId.equals(other.toyId))
			return false;
		return true;
	}



	@Override
	public int compareTo(Toy o) {
		
		return this.arrivalTime - o.arrivalTime;
	}

	@Override
	public String toString() {
		return "Toy [toyId=" + toyId + ", arrivalTime=" + arrivalTime
				+ ", timeToBuild=" + timeToBuild + ", idealEndTime="
				+ idealEndTime + ", idealStartTime=" + idealStartTime + "]";
	}
	
	

	
	
}
