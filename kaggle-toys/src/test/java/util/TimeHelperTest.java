package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeHelperTest {

	@Test
	public void testParseTime() {
		
		System.out.println("Max int : " + Integer.MAX_VALUE);
		
		int time = TimeHelper.parseTime("2538 4 7 17 49");
		System.out.println(time);
		
		time = TimeHelper.parseTime("2014 1 1 1 5");
		System.out.println(time);
		
		time = TimeHelper.parseTime("2014 1 1 9 0");
		System.out.println(time);
	}
	
	@Test
	public void testConvertToString() {
		
		System.out.println(TimeHelper.convertToString(TimeHelper.parseTime("2014 1 1 0 0")));
		
		System.out.println(TimeHelper.convertToString(186900));
		
		System.out.println(TimeHelper.convertToString(5));
		
		System.out.println(TimeHelper.convertToString(65));
	}
	
	@Test
	public void testGetNextStartTime()
	{
		//System.out.println(TimeHelper.convertToString(TimeHelper.getNextStartTime(TimeHelper.parseTime("2014 1 1 20 5"))));
		
		System.out.println(TimeHelper.convertToString(TimeHelper.getNextStartTime(TimeHelper.parseTime("2014 5 10 19 0"))));
	}

}
