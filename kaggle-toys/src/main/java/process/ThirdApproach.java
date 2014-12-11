package process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import data.Elve;
import data.Toy;

import util.JobTransferHelper;
import util.ObjectiveValueHelper;
import util.OutputFileHelper;
import util.RandomSelector;
import util.RandomSelectorWithoutReplacement;
import util.ToysAssignmentReader;

public class ThirdApproach {
	private static final String outputFolder = "D:/Kaggle/HelpingSantasHelpers/output/thirdApproach/";
	//private static final String initialAssignmentsFile = "D:\\Kaggle\\HelpingSantasHelpers\\output\\firstSecondApproach\\2014_12_6_23_48_final\\part-r-00000";
	private static final String initialAssignmentsFile = "D:\\Kaggle\\HelpingSantasHelpers\\output\\firstSecondApproach\\2014_12_12_0_27_final\\part-r-00000";
	private static final double TOTAL_RANDOM_TRANSFER_PROB = 0.3;


	public static void main(String args[]) throws IOException {
		System.out.println("By third approach...");

		List<Toy>[] assignments = ToysAssignmentReader
				.readAssignments(initialAssignmentsFile);

		Elve[] elves = new Elve[901];
		for (int i = 1; i < 901; i++) {
			elves[i] = new Elve(i);
		}

		ObjectiveValueHelper.ObjectiveValueData objData = ObjectiveValueHelper
				.calculateObjectiveValue(elves, assignments);
		
		BufferedWriter initialDataWriter = new BufferedWriter(new FileWriter(outputFolder + "/initialInfo.txt"));
		initialDataWriter.write("initial obj vaue " + objData.val + "\n");
		initialDataWriter.write("initial elves used " + objData.elvesUsed + "\n");
		initialDataWriter.close();
	
		
		BufferedWriter stepsWriter = new BufferedWriter(new FileWriter(outputFolder + "/StepsInfo.txt"));
		
		
		
		double temp = 1000;
		double coolingFactor = 0.001;

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
		int totalRandomAccepted = 0;
		int totalIterationsAccepted = 0;
		int totalWorseIterationsAccepted = 0;
		int numOfSameTransferElves = 0;
		int zeroToysFromElveId = 0;
		int totalBetterIterations = 0;
		int totalRandomBetterIterations = 0;

		int maxEndTime = objData.endTime;
		int maxEndtimeEvleId = objData.maxEndTimeElveId;
		int elvesUsed = objData.elvesUsed;
		double objVal = objData.val;
		
		
		List<Toy>[] bestAssignments = copyAssignments(assignments);

		Random random = new Random();

		while (temp > 1) {
			iter++;
			if(iter % 1000 == 0)
			{
				System.out.println("iter : " + iter);
			}
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
				Toy toyToTransfer = assignments[fromElveId].get(random
						.nextInt(numFromElveAssignments));
				JobTransferHelper.JobTransferResult transferResult = JobTransferHelper
						.getJobTransferResult(fromElveId, toElveId,
								assignments[fromElveId], assignments[toElveId],
								toyToTransfer);

				int newMaxEntimeElveId;
				int newMaxEndTime;
				int newElvesUsed;
				if (maxEndtimeEvleId == transferResult.endTimeElveId
						|| maxEndTime > transferResult.endTime) {
					newMaxEntimeElveId = transferResult.endTimeElveId;
					newMaxEndTime = transferResult.endTime;
				} else {
					newMaxEntimeElveId = maxEndtimeEvleId;
					newMaxEndTime = maxEndTime;
				}

				newElvesUsed = elvesUsed;
				if (numFromElveAssignments == 1) {
					newElvesUsed++;
				}

				if (assignments[toElveId].size() == 0) {
					newElvesUsed--;
				}

				double newObjVal = ObjectiveValueHelper
						.calculateObjectiveValue(newMaxEndTime, newElvesUsed);
				boolean accept;
				if(newObjVal < objVal)
				{
					totalBetterIterations++;
					accept = true;
					if(totalRandomSelection)
					{
						totalRandomBetterIterations++;
					}
					
				}
				else
				{
					accept = acceptChange(objVal, newObjVal, temp);
					stepsWriter.write("prevObjVal : " + objVal + ", newObjVal : " + newObjVal + " temp : " + temp + " accepted : " + accept + "\n");
				}
				
				Elve newFromElve;
				Elve newToElve;
				if (accept) {
					newFromElve = transferResult.fromElve;
					newToElve = transferResult.toElve;

					assignments[fromElveId].remove(toyToTransfer);
					insertToy(assignments[toElveId], toyToTransfer);
					
					totalIterationsAccepted++;
					if(totalRandomSelection)
					{
						totalRandomAccepted++;
					}
					
					if(newObjVal < objVal)
					{
						bestAssignments = copyAssignments(assignments);
					}
					
					if(newObjVal > objVal)
					{
						totalWorseIterationsAccepted++;
					}
					
					maxEndTime = newMaxEndTime;
					maxEndtimeEvleId = newMaxEntimeElveId;
					elvesUsed = newElvesUsed;
					objVal = newObjVal;

				} else {

					newFromElve = elves[fromElveId];
					newToElve = elves[toElveId];
				}

				elves[fromElveId] = newFromElve;
				elves[toElveId] = newToElve;

			} else {
				zeroToysFromElveId++;

			}
			

			if (!totalRandomSelection) {
				fromElveSelector.addElement(fromElveId,
						elves[fromElveId].getLastJobFinishTime());
				toElveSelector.addElement(fromElveId,
						elves[toElveId].getLastJobFinishTime());
			}
			
			
			
			temp = temp*coolingFactor;
			
			System.out.println();

		}
		
		stepsWriter.close();
		
		OutputFileHelper outputFileHelper = new OutputFileHelper(outputFolder);
		outputFileHelper.writeAllAssignments(assignments);
		outputFileHelper.done();
		
		
		System.out.println("iter : " + iter);
		System.out.println("totalRandomIterations : " + totalRandomIterations);
		System.out.println("totalRandomAccepted : " + totalRandomAccepted);
		System.out.println("totalIterationsAccepted : " + totalIterationsAccepted);
		System.out.println("totalWorseIterationsAccepted : " + totalWorseIterationsAccepted);
		System.out.println("numOfSameTransferElves : " + numOfSameTransferElves);
		System.out.println("zeroToysFromElveId : " + zeroToysFromElveId);
		System.out.println("totalBetterIterations : " + totalBetterIterations);
		System.out.println("totalRandomBetterIterations : " + totalRandomBetterIterations);
		System.out.println("elvesUsed : " + elvesUsed);

	}
	
	private static List<Toy>[] copyAssignments(List<Toy>[] assignments)
	{
		List<Toy>[] copy = new ArrayList[assignments.length];
		for(int i=1;i<901;i++)
		{
			List<Toy> list = assignments[i];
			List<Toy> newList = new ArrayList<Toy>(list.size());
			newList.addAll(list);
			
			copy[i] = newList;
		}
		
		return copy;
	}

	private static void insertToy(List<Toy> list, Toy toy) {
		int insertPos = 0;

		for (Toy toyEntry : list) {
			if (toyEntry.getArrivalTime() > toy.getArrivalTime()) {
				break;
			}

			insertPos++;
		}

		list.add(insertPos, toy);
	}

	public static boolean acceptChange(double prevObj, double newObj,
			double temp) {
		
		double acceptanceProb = calculateAcceptanceProb(prevObj, newObj, temp);

		return RandomSelector
				.selectRandomly(acceptanceProb, 1 - acceptanceProb);
	}

	public static double calculateAcceptanceProb(double prevObj, double newObj,
			double temp) {
		if (prevObj > newObj)
			return 1.0;

		return Math.exp((newObj - prevObj) / temp);
	}

}
