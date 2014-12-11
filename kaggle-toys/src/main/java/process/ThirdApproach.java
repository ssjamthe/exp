package process;

import java.util.List;
import java.util.Random;

import data.Elve;
import data.Toy;

import util.JobTransferHelper;
import util.ObjectiveValueHelper;
import util.RandomSelector;
import util.RandomSelectorWithoutReplacement;
import util.ToysAssignmentReader;

public class ThirdApproach {
	private static final String outputFolder = "D:/Kaggle/HelpingSantasHelpers/output/thirdApproach/";
	private static final String initialAssignmentsFile = "D:\\Kaggle\\HelpingSantasHelpers\\output\\firstSecondApproach\\2014_12_6_23_48_final\\part-r-00000";
	private static final double TOTAL_RANDOM_TRANSFER_PROB = 0.3;

	public static void main(String args[]) {
		System.out.println("By third approach...");

		List<Toy>[] assignments = ToysAssignmentReader
				.readAssignments(initialAssignmentsFile);

		Elve[] elves = new Elve[901];
		for (int i = 1; i < 901; i++) {
			elves[i] = new Elve(i);
		}

		ObjectiveValueHelper.ObjectiveValueData objData = ObjectiveValueHelper
				.calculateObjectiveValue(elves, assignments);
		double temp = 1000;

		RandomSelectorWithoutReplacement<Integer> fromElveSelector = new RandomSelectorWithoutReplacement<Integer>();
		for (int i = 1; i < 901; i++) {
			fromElveSelector.addElement(i,
					(double) elves[i].getLastJobFinishTime());
		}

		RandomSelectorWithoutReplacement<Integer> toElveSelector = new RandomSelectorWithoutReplacement<Integer>();
		for (int i = 1; i < 901; i++) {
			toElveSelector.addElement(i, 1.0 / elves[i].getLastJobFinishTime());
		}

		int iter = 0;
		int totalRandomIterations = 0;
		int totalRandomAxccepted = 0;
		int totalIterationsAccpted = 0;
		int totalWorseIterationsAccepted = 0;
		int numOfSameTransferElves = 0;
		int zeroToysFromElveId = 0;

		int maxEndTime = objData.endTime;
		int maxEntimeEvleId = objData.maxEndTimeElveId;

		Random random = new Random();

		while (temp > 1) {
			iter++;
			boolean totalRandomSelection = RandomSelector.selectRandomly(
					TOTAL_RANDOM_TRANSFER_PROB, 1 - TOTAL_RANDOM_TRANSFER_PROB);
			int fromElveId;
			int toElveId;
			if (totalRandomSelection) {
				totalRandomIterations++;
				fromElveId = random.nextInt(900) + 1;
				toElveId = random.nextInt(900) + 1;

				if (fromElveId == toElveId) {
					numOfSameTransferElves++;
					continue;
				}

			} else {

				fromElveId = fromElveSelector.selectElement();
				toElveId = toElveSelector.selectElement();
			}
			
			int numFromElveAssignments = assignments[fromElveId].size();

			if (numFromElveAssignments > 0) {
				Toy toyToTransfer = assignments[fromElveId].get(random.nextInt(numFromElveAssignments));
				JobTransferHelper.JobTransferResult transferResult = JobTransferHelper
						.getJobTransferResult(fromElveId, toElveId,
								assignments[fromElveId], assignments[toElveId],
								toyToTransfer);
			}
			else
			{
				zeroToysFromElveId++;
				if(totalRandomSelection)
				{
					fromElveSelector.addElement(fromElveId, elves[fromElveId].getLastJobFinishTime());
					toElveSelector.addElement(fromElveId, elves[fromElveId].getLastJobFinishTime());
				}
			}

		}

	}

	private static double calculateAcceptanceProb(double prevObj,
			double newObj, double temp) {
		if (prevObj > newObj)
			return 1.0;

		return Math.exp((newObj - prevObj) / temp);
	}

}
