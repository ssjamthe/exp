package util;

import java.util.List;
import java.util.Map;

import data.Elve;
import data.Toy;

public class ObjectiveValueHelper {

	public static class ObjectiveValueData {
		public double val;
		public int endTime;
		public String maxEndTimeElveId;
	}

	

	public ObjectiveValueData calculateObjectiveValue(Elve[] elves,
			Map<String, List<Toy>> assignments) {
		int maxTime = 0;
		String maxEndTimeElveId = null;
		int elvesUsed = 0;

		for (Elve elve : elves) {
			List<Toy> toys = assignments.get(elve.getId());
			if (toys.size() == 0)
				continue;

			elvesUsed++;

			for (Toy toy : toys) {
				elve.work(toy);
			}

			int lastToyEndTime = elve.getLastJobFinishTime();

			if (lastToyEndTime > maxTime) {
				maxTime = lastToyEndTime;
				maxEndTimeElveId = elve.getId();
			}

		}

		ObjectiveValueData ovd = new ObjectiveValueData();
		ovd.endTime = maxTime;
		ovd.val = maxTime * Math.log(1 + elvesUsed);
		ovd.maxEndTimeElveId = maxEndTimeElveId;

		return ovd;

	}

	
}
