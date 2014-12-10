package util;

import java.util.List;

import data.Elve;
import data.Toy;

public class JobTransferHelper {

	public static class JobTransferResult {
		public int endTime;
		public String enTimeElveId;
	}

	public JobTransferResult getJobTransferResult(
			List<Toy> fromElveAssignments, List<Toy> toElveAssignments, Toy toy) {

		Elve fromElve = new Elve("dummy1");
		Elve toElve = new Elve("dummy2");

		if (toElveAssignments.size() == 0) {
			toElve.work(toy);
		} else {
			boolean assigned = false;
			for (Toy currToy : toElveAssignments) {
				if (currToy.getArrivalTime() > toy.getArrivalTime()) {
					toElve.work(toy);
					assigned = true;
				} else {
					toElve.work(currToy);
				}
			}

			if (!assigned) {
				toElve.work(toy);
			}
		}

		if (fromElveAssignments.size() > 1) {
			for (Toy currToy : toElveAssignments) {
				if (!toy.getToyId().equals(currToy.getToyId())) {
					fromElve.work(currToy);
				}
			}
		}

		JobTransferResult result = new JobTransferResult();
		if (fromElve.getLastJobFinishTime() > toElve.getLastJobFinishTime()) {
			result.endTime = fromElve.getLastJobFinishTime();
			result.enTimeElveId = fromElve.getId();
		} else {
			result.endTime = toElve.getLastJobFinishTime();
			result.enTimeElveId = toElve.getId();
		}

		return result;

	}

}
