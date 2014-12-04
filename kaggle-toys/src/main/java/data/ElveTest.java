package data;

import static org.junit.Assert.*;

import org.junit.Test;

import util.TimeHelper;

import data.Elve.WorkInfo;

public class ElveTest {

	@Test
	public void testWork() {

		Elve elve = new Elve("1");

		Toy toy = new Toy();
		toy.setValues("1", "2014 1 1 0 5", 10);

		WorkInfo workInfo = elve.work(toy);

		System.out.println("startTime : " + workInfo.startTime
				+ ", duration : " + workInfo.duration);

		elve = new Elve("1");
		elve.setRating(0.5);

		toy.setValues("1", "2014 1 1 0 5", 10);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + workInfo.startTime
				+ ", duration : " + workInfo.duration);

		elve = new Elve("1");
		elve.setRating(1.0);

		toy.setValues("1", "2014 1 1 0 5", 700);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + workInfo.startTime
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));
		
		
		elve = new Elve("1");
		elve.setRating(1.0);

		toy.setValues("1", "2014 1 1 9 0", 700);

		workInfo = elve.work(toy);

		System.out.println("startTime : " + workInfo.startTime
				+ ", duration : " + workInfo.duration
				+ ",eleve available time : "
				+ TimeHelper.convertToString(elve.getNextAvailableTime()));

	}

}
