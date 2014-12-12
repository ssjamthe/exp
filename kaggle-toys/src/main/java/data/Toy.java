package data;

import util.TimeHelper;

public class Toy implements Comparable<Toy>{
	
	
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

	
	
}
