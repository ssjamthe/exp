package util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import data.Elve;
import data.Toy;

public class ObjectiveValueHelper {
	
	private static final double MULTPLIER = Math.pow(10, -9);

	public static class ObjectiveValueData {
		public double val;
		public int endTime;
		public int maxEndTimeElveId;
		public int elvesUsed;
		public Elve[] elves;
	}

	
/**
 * Modifies the elves
 */
	public static ObjectiveValueData calculateObjectiveValue(Elve[] elves,
			List<Toy>[] assignments) {
		int maxTime = 0;
		int maxEndTimeElveId = -1;
		int elvesUsed = 0;

		for (int i=1;i<901;i++) {
			Elve elve = elves[i];
			List<Toy> toys = assignments[i];
			
			if (toys.size() == 0)
				continue;

			elvesUsed++;

			for (Toy toy : toys) {
				elve.work(toy);
			}

			int lastToyEndTime = elve.getLastJobFinishTime();

			if (maxTime < lastToyEndTime) {
				maxTime = lastToyEndTime;
				maxEndTimeElveId = elve.getId();
			}

		}

		ObjectiveValueData ovd = new ObjectiveValueData();
		ovd.endTime = maxTime;
		ovd.val = calculateObjectiveValue(maxTime,elvesUsed);
		ovd.maxEndTimeElveId = maxEndTimeElveId;
		ovd.elvesUsed = elvesUsed;
		ovd.elves = elves;

		return ovd;

	}
	
	public static ObjectiveValueData calculateObjectiveValue(List<Toy>[] assignments) {
		
		Elve[] elves = new Elve[901];
		
		for(int i=1;i<901;i++)
		{
			elves[i] = new Elve(i);
		}
		
		return calculateObjectiveValue(elves,assignments);
		
	}
	
	
	public static double calculateObjectiveValue(int endTime,int elvesUsed)
	{
		return endTime * Math.log(1 + elvesUsed);
	}

	
}
