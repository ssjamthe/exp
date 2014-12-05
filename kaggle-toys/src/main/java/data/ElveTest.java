package data;

import static org.junit.Assert.*;

import org.junit.Test;

import util.TimeHelper;

import data.Elve.WorkInfo;

public class ElveTest {

	@Test
	public void testWork() {

		Elve elve = new Elve("1");
		//elve.setNextAvailableTime(TimeHelper.parseTime("2014 1 1 0 44"));

		Toy toy = new Toy();
		toy.setValues("1", "2014 1 1 0 44", 2);

		WorkInfo workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration);

		//elve = new Elve("1");
		//elve.setRating(0.5);

		toy.setValues("1", "2014 1 1 1 15", 2);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration);

		//elve = new Elve("1");
		//elve.setRating(1.0);

		toy.setValues("1", "2014 1 1 1 22", 19);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		
		//elve = new Elve("1");
		//elve.setRating(1.0);

		toy.setValues("1", "2014 1 1 2 34", 6);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		
		toy.setValues("1", "2014 1 1 2 53", 3);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		toy.setValues("1", "2014 1 1 3 3", 18);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		toy.setValues("1", "2014 1 1 4 5", 9);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		toy.setValues("1", "2014 1 1 4 28", 10);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		toy.setValues("1", "2014 1 1 4 59", 4);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		toy.setValues("1", "2014 1 1 5 10", 33);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		toy.setValues("1", "2014 1 1 6 54", 39);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		toy.setValues("1", "2014 1 1 8 53", 8);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		toy.setValues("1", "2014 1 1 9 18", 29);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		toy.setValues("1", "2014 1 1 10 48", 1);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + TimeHelper.convertToString(workInfo.startTime)
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
	}

}
