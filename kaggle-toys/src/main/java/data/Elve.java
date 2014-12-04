package data;

import java.util.Calendar;

import util.TimeHelper;

/*
 * TODO : Of course create separate time util class...
 */
public class Elve {
	
	private static final long FIRST_JAN;
	private static final int MIN_IN_DAY = 24*60;
	private static final int START_SANCTIONED_IN_DAY = 9*60;
	private static final int END_SANCTIONED_IN_DAY = 19*60;
	private static final int SACNTIONED_IN_DAY = 10*60;
	private static final int UNSANCTIONED_IN_DAY = 14*10;
	
	private final String id;
	private double rating = 1.0;
	private int nextAvailableTime=540;
	
	static
	{
		Calendar c = Calendar.getInstance();
		c.set(2014, 1, 1, 0, 0);
		FIRST_JAN = c.getTimeInMillis();
	}
	
	public static class WorkInfo
	{
		int startTime;
		int duration;
		
		
		public int getStartTime() {
			return startTime;
		}
		public void setStartTime(int startTime) {
			this.startTime = startTime;
		}
		public int getDuration() {
			return duration;
		}
		public void setDuration(int duration) {
			this.duration = duration;
		}
		
		
	}
	
	
	private static int getSanctionedTimeForDuration(int startTime,int duration)
	{
		int fullDays = duration/MIN_IN_DAY;
		int sanctioned = SACNTIONED_IN_DAY * fullDays;
		
		int remainderStart = startTime + fullDays*MIN_IN_DAY;
		
		return sanctioned + getSanctionedMinsForRange(remainderStart,startTime + duration);
	}
	
	/*
	 * startTime and endTime should belong to single day
	 */
	private static int getSanctionedMinsForRange(int startTime,int endTime)
	{
		int dayStart = startTime - startTime%MIN_IN_DAY;
		int startSanctionedTime = dayStart + START_SANCTIONED_IN_DAY;
		int endSanctionedTime = dayStart + END_SANCTIONED_IN_DAY;
		
		
		if(startTime >= startSanctionedTime && startTime<=endSanctionedTime)
		{
			if(endTime <= endSanctionedTime)
			{
				return endTime - startTime;
			}
			else
			{
				return endSanctionedTime - startTime;
			}
		}
		else if(startTime<=startSanctionedTime)
		{
			if(endTime <= startSanctionedTime)
			{
				return 0;
			}
			else if(endTime <= endSanctionedTime)
			{
				return endTime - startSanctionedTime;
			}
			else
			{
				return endSanctionedTime - startSanctionedTime;
			}
		}
		else
		{
			return 0;
		}
		
	}
	
	
	public Elve(String id)
	{
		this.id = id;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getNextAvailableTime() {
		return nextAvailableTime;
	}

	public void setNextAvailableTime(int nextAvailableTime) {
		this.nextAvailableTime = nextAvailableTime;
	}

	public String getId() {
		return id;
	}

	
	
	public WorkInfo work(Toy toy)
	{
		int startTime = toy.getArrivalTime() > this.nextAvailableTime?toy.getArrivalTime():this.nextAvailableTime;
		startTime = TimeHelper.getNextStartTime(startTime);
		int timeRequired = (int) Math.ceil(toy.getTimeToBuild()/this.rating);
		int sanctionedMinutes = getSanctionedTimeForDuration(startTime, timeRequired);
		int unsanctionedMinutes = timeRequired - sanctionedMinutes;
		updateRating(sanctionedMinutes,unsanctionedMinutes);
		updateAvailableTime(startTime, sanctionedMinutes, unsanctionedMinutes);
		
		WorkInfo workInfo = new WorkInfo();
		workInfo.startTime = startTime;
		workInfo.duration = timeRequired;
		return workInfo;
	}
	
	private void updateAvailableTime(int startTime,int sanctionedMinutes,int unsanctionedMinutes)
	{
		int workEndTime = startTime + sanctionedMinutes + unsanctionedMinutes;
		this.nextAvailableTime = addToSanctionedTime(TimeHelper.getNextStartTime(workEndTime),unsanctionedMinutes);
	}
	
	private static int addToSanctionedTime(int startTimeInSanctioned,int hoursToAdd)
	{
		int dayStart = startTimeInSanctioned - startTimeInSanctioned%MIN_IN_DAY;
		int startSanctionedTime = dayStart + START_SANCTIONED_IN_DAY;
		int endSanctionedTime = dayStart + END_SANCTIONED_IN_DAY;
		
		int currStartTime = startTimeInSanctioned;
		
		if(endSanctionedTime - startTimeInSanctioned > hoursToAdd)
		{
			return startTimeInSanctioned + hoursToAdd;
		}
		else
		{
			hoursToAdd = hoursToAdd - (endSanctionedTime - startTimeInSanctioned);
			currStartTime = getNextDaySanctionedStart(currStartTime);
		}
		
		while(hoursToAdd > SACNTIONED_IN_DAY)
		{
			currStartTime = getNextDaySanctionedStart(currStartTime);
			hoursToAdd = hoursToAdd - SACNTIONED_IN_DAY;
		}
		
		return currStartTime + hoursToAdd;
		
	}
	
	private static int getNextDaySanctionedStart(int prevDayTime)
	{
		int nextDaySameTime = prevDayTime + MIN_IN_DAY;
		int nextDayStart = nextDaySameTime - nextDaySameTime%MIN_IN_DAY;
		int nextDayStartSanctionedTime = nextDayStart + START_SANCTIONED_IN_DAY;
		
		return nextDayStartSanctionedTime;
	}
	
	
	private void updateRating(int sanctionedMinutes,int unsanctionedMinutes)
	{
		double newRating = this.rating * Math.pow(1.02, sanctionedMinutes/60.0) * Math.pow(0.9, unsanctionedMinutes/60.0);
		if(newRating < 0.25)
			newRating = 0.25;
		else if(newRating > 4.0)
			newRating = 4.0;
		
		
		this.rating = newRating;
	}
	
	
}
