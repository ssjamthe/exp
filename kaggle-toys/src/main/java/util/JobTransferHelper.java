package util;

import java.util.List;

import data.Elve;
import data.Toy;

public class JobTransferHelper {

	public static class JobTransferResult {
		public Elve fromElve;
		public Elve toElve;
		public int endTime;
		public int endTimeElveId;
	}

	public static JobTransferResult getJobTransferResult(int fromElveId,int toElveId,
			List<Toy> fromElveAssignments, List<Toy> toElveAssignments, Toy toy,Elve[] elves) {

		Elve fromElve = new Elve(fromElveId);
		Elve toElve = new Elve(toElveId);

		if (toElveAssignments.size() == 0) {
			toElve.work(toy);
		} else {
			boolean assigned = false;
			for (Toy currToy : toElveAssignments) {
				if((currToy.getArrivalTime() >  toy.getArrivalTime()) && !assigned)
				{
						toElve.work(toy);
						assigned = true;
				}
				
				toElve.work(currToy);
			}
			
			if(!assigned)
			{
				toElve.work(toy);
			}
		}
		
			for (Toy currToy : fromElveAssignments) {
				if (!toy.getToyId().equals(currToy.getToyId())) {
					fromElve.work(currToy);
				}
			}
		
		
		int maxTime = 0;
		int maxTimeElveId = -1;
		
		for(int i=1;i<901;i++)
		{
			Elve elve;
			if(i == fromElveId)
				elve = fromElve;
			else if(i == toElveId)
				elve = toElve;
			else
				elve = elves[i];
			
			if(maxTime < elve.getLastJobFinishTime())
			{
				maxTime = elve.getLastJobFinishTime();
				maxTimeElveId = elve.getId();
			}
		}

		JobTransferResult result = new JobTransferResult();
		result.fromElve = fromElve;
		result.toElve = toElve;
		result.endTime = maxTime;
		result.endTimeElveId = maxTimeElveId;
		

		return result;

	}

}
