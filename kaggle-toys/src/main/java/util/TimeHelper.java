package util;

import java.util.Calendar;

public class TimeHelper {

	private static final long FIRST_JAN;
	private static final int MIN_IN_DAY = 24*60;
	private static final int START_SANCTIONED_IN_DAY = 9*60;
	private static final int END_SANCTIONED_IN_DAY = 19*60;
	private static final int SACNTIONED_IN_DAY = 10*60;
	private static final int UNSANCTIONED_IN_DAY = 14*10;

	static {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(2014, 0, 1, 0, 0);
		FIRST_JAN = c.getTimeInMillis();
	}

	public static int parseTime(String timeStr) {
		Calendar c = Calendar.getInstance();

		String[] splits = timeStr.split("\\s+");
		
		c.clear();
		c.set(Integer.parseInt(splits[0]), Integer.parseInt(splits[1]) - 1,
				Integer.parseInt(splits[2]), Integer.parseInt(splits[3]),
				Integer.parseInt(splits[4]));
		

		return (int) ((c.getTimeInMillis() - FIRST_JAN) / (1000*60));
	}

	public static String convertToString(int time) {
		Calendar c = Calendar.getInstance();
		c.set(2014, 0, 1,0,0);
		c.add(Calendar.MINUTE, time);

		return c.get(Calendar.YEAR) + " " + (c.get(Calendar.MONTH) + 1) + " "
				+ c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY)
				+ " " + c.get(Calendar.MINUTE);
	}
	
	public static int getNextStartTime(int time)
	{
		int dayStart = time - time%MIN_IN_DAY;
		int startSanctionedTime = dayStart + START_SANCTIONED_IN_DAY;
		int endSanctionedTime = dayStart + END_SANCTIONED_IN_DAY;
		
		if(time < startSanctionedTime)
		{
			return startSanctionedTime;
		}
		else if(time >= endSanctionedTime)
		{
			int nextDaySameTime = time + MIN_IN_DAY;
			
			int nextDayStart = nextDaySameTime - nextDaySameTime%MIN_IN_DAY;
			int nextDayStartSanctionedTime = nextDayStart + START_SANCTIONED_IN_DAY;
			
			return nextDayStartSanctionedTime;
		}
		else
		{
			return time;
		}
	}

}
