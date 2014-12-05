package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeHelperTest {

	@Test
	public void testParseTime() {
		
		int time = TimeHelper.parseTime("2014 1 1 12 10");
		System.out.println(time);
		
		time = TimeHelper.parseTime("2014 1 1 1 5");
		System.out.println(time);
		
		time = TimeHelper.parseTime("2014 1 1 9 0");
		System.out.println(time);
	}
	
	@Test
	public void testConvertToString() {
		
		System.out.println(TimeHelper.convertToString(725));
		
		System.out.println(TimeHelper.convertToString(5));
		
		System.out.println(TimeHelper.convertToString(65));
	}
	
	@Test
	public void testGetNextStartTime()
	{
		//System.out.println(TimeHelper.convertToString(TimeHelper.getNextStartTime(TimeHelper.parseTime("2014 1 1 20 5"))));
		
		System.out.println(TimeHelper.convertToString(TimeHelper.getNextStartTime(TimeHelper.parseTime("2014 1 1 12 5"))));
	}

}
