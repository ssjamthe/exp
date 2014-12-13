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
import util.AssignmentsDistributionWriter;
import util.JobTransferHelper;
import util.ObjectiveValueHelper;
import util.OutputFileHelper;
import util.RandomSelector;
import util.RandomSelectorWithoutReplacement;
import util.ToysAssignmentReader;

public class FourthApproach {
	private static final String outputFolder = "D:/Kaggle/HelpingSantasHelpers/output/fourthApproach/";
	// private static final String initialAssignmentsFile =
	// "D:\\Kaggle\\HelpingSantasHelpers\\output\\firstSecondApproach\\2014_12_6_23_48_final\\part-r-00000";
	private static final String initialAssignmentsFile = "D:\\Kaggle\\HelpingSantasHelpers\\output\\firstSecondApproach\\2014_12_12_0_27_final\\part-r-00000";
	private static final double TOTAL_RANDOM_TRANSFER_PROB = 0.3;
	private static final double ACCEPT_SAME_OBJ_VAL_PROB = 0.5;

	public static void main(String args[]) throws IOException {
		System.out.println("By fourth approach...");

		List<Toy>[] assignments = ToysAssignmentReader
				.readAssignments(initialAssignmentsFile);

		Elve[] elves = new Elve[901];
		for (int i = 1; i < 901; i++) {
			elves[i] = new Elve(i);
		}

		ObjectiveValueHelper.ObjectiveValueData objData = ObjectiveValueHelper
				.calculateObjectiveValue(elves, assignments);

		BufferedWriter initialDataWriter = new BufferedWriter(new FileWriter(
				outputFolder + "/initialInfo.txt"));
		initialDataWriter.write("initial obj vaue " + objData.val + "\n");
		initialDataWriter.write("initial elves used " + objData.elvesUsed
				+ "\n");
		initialDataWriter.write("initial max end time " + objData.endTime
				+ "\n");
		initialDataWriter.write("initial max end time elve id "
				+ objData.maxEndTimeElveId + "\n");
		initialDataWriter.close();

		AssignmentsDistributionWriter.writeAssignments(outputFolder
				+ "/initialAssignmentsDistribution.txt", assignments);

		BufferedWriter stepsWriter = new BufferedWriter(new FileWriter(
				outputFolder + "/StepsInfo.txt"));

		double temp = 20;
		double minTemp = 1;
		double coolingFactor = 0.9999935;

		RandomSelectorWithoutReplacement<Integer> toElveSelector = new RandomSelectorWithoutReplacement<Integer>();
		for (int i = 1; i < 901; i++) {
			toElveSelector.addElement(i, 1.0 / elves[i].getLastJobFinishTime());
		}

		int iter = 0;
		int totalRandomIterations = 0;
		int totalRandomAccepted = 0;
		int totalIterationsAccepted = 0;
		int totalWorseIterationsAccepted = 0;
		int totalWorseIterations = 0;
		int numOfSameTransferElves = 0;
		int zeroToysFromElveId = 0;
		int totalBetterIterations = 0;
		int totalRandomBetterIterations = 0;
		int totalBestIterations = 0;
		int totalSameObjValInterations = 0;
		int totalSameObjValInterationsAccepted = 0;

		int maxEndTime = objData.endTime;
		int maxEndtimeEvleId = objData.maxEndTimeElveId;
		int elvesUsed = objData.elvesUsed;
		double objVal = objData.val;

		List<Toy>[] bestAssignments = copyAssignments(assignments);
		int bestAssignmentsElvesUsed = objData.elvesUsed;
		double bestAssignmentsObjVal = objData.val;
		int bestAssignmentsMaxTime = objData.endTime;
		int bestAssignmentsMaxTimeElvdId = objData.maxEndTimeElveId;

		Random random = new Random();

		while (temp > minTemp) {
			iter++;
			if (iter % 10000 == 0) {
				System.out.println("iter : " + iter + ", temp : " + temp);
			}
			boolean totalRandomSelection = RandomSelector.selectRandomly(
					TOTAL_RANDOM_TRANSFER_PROB, 1 - TOTAL_RANDOM_TRANSFER_PROB);
			final int fromElveId = maxEndtimeEvleId;
			int toElveId;
			if (totalRandomSelection) {
				totalRandomIterations++;

				toElveId = random.nextInt(900) + 1;

				if (fromElveId == toElveId) {
					numOfSameTransferElves++;
					continue;
				}

			} else {

				toElveId = toElveSelector.selectElement();

			}

			int numFromElveAssignments = assignments[fromElveId].size();

			if (numFromElveAssignments > 0) {
				Toy toyToTransfer = assignments[fromElveId].get(random
						.nextInt(numFromElveAssignments));
				JobTransferHelper.JobTransferResult transferResult = JobTransferHelper
						.getJobTransferResult(fromElveId, toElveId,
								assignments[fromElveId], assignments[toElveId],
								toyToTransfer, elves);

				int newMaxEntimeElveId = transferResult.endTimeElveId;
				int newMaxEndTime = transferResult.endTime;
				int newElvesUsed;

				newElvesUsed = elvesUsed;
				if (numFromElveAssignments == 1) {
					newElvesUsed--;
				}

				if (assignments[toElveId].size() == 0) {
					newElvesUsed++;
				}

				double newObjVal = ObjectiveValueHelper
						.calculateObjectiveValue(newMaxEndTime, newElvesUsed);
				boolean accept;
				if (newObjVal < objVal) {
					totalBetterIterations++;
					accept = true;
					if (totalRandomSelection) {
						totalRandomBetterIterations++;
					}

				} else if (newObjVal > objVal) {
					totalWorseIterations++;
					accept = acceptChange(objVal, newObjVal, temp);
					// stepsWriter.write("prevObjVal : " + objVal +
					// ", newObjVal : " + newObjVal + " temp : " + temp +
					// " accepted : " + accept + "\n");
				} else {
					totalSameObjValInterations++;
					if (Math.random() < ACCEPT_SAME_OBJ_VAL_PROB) {
						accept = true;
						totalSameObjValInterationsAccepted++;
					} else {
						accept = false;
					}
				}

				Elve newFromElve;
				Elve newToElve;
				if (accept) {
					newFromElve = transferResult.fromElve;
					newToElve = transferResult.toElve;

					assignments[fromElveId].remove(toyToTransfer);
					insertToy(assignments[toElveId], toyToTransfer);

					// Collections.sort(assignments[fromElveId]);
					// Collections.sort(assignments[toElveId]);

					totalIterationsAccepted++;
					if (totalRandomSelection) {
						totalRandomAccepted++;
					}

					if (newObjVal > objVal) {
						totalWorseIterationsAccepted++;
					}

					if (newObjVal < bestAssignmentsObjVal) {
						bestAssignments = copyAssignments(assignments);
						ObjectiveValueHelper.ObjectiveValueData test = ObjectiveValueHelper
								.calculateObjectiveValue(bestAssignments);
						System.out.println("newTest : " + test.val + ", new : "
								+ newObjVal + " , testEndTime : "
								+ test.endTime + ", endTime : " + newMaxEndTime
								+ ", testElveId : " + test.maxEndTimeElveId
								+ " , elveId : " + newMaxEntimeElveId + " , testElvesUsed : " + test.elvesUsed + " ,elvesUsed : " + newElvesUsed);
						bestAssignmentsElvesUsed = newElvesUsed;
						bestAssignmentsObjVal = newObjVal;
						bestAssignmentsMaxTime = newMaxEndTime;
						bestAssignmentsMaxTimeElvdId = newMaxEntimeElveId;
						totalBestIterations++;
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
				toElveSelector.addElement(toElveId,
						elves[toElveId].getLastJobFinishTime());
			}

			temp = temp * coolingFactor;

		}

		stepsWriter.close();

		AssignmentsDistributionWriter.writeAssignments(outputFolder
				+ "assignmentsDistribution.txt", bestAssignments);

		BufferedWriter summaryWriter = new BufferedWriter(new FileWriter(
				outputFolder + "/summary.txt"));
		summaryWriter.write("\niter : " + iter);
		summaryWriter.write("\ntotalRandomIterations : "
				+ totalRandomIterations);
		summaryWriter.write("\ntotalRandomAccepted : " + totalRandomAccepted);
		summaryWriter.write("\ntotalIterationsAccepted : "
				+ totalIterationsAccepted);
		summaryWriter.write("\ntotalWorseIterations : " + totalWorseIterations);
		summaryWriter.write("\ntotalWorseIterationsAccepted : "
				+ totalWorseIterationsAccepted);
		summaryWriter.write("\nnumOfSameTransferElves : "
				+ numOfSameTransferElves);
		summaryWriter.write("\nzeroToysFromElveId : " + zeroToysFromElveId);
		summaryWriter.write("\ntotalBetterIterations : "
				+ totalBetterIterations);
		summaryWriter.write("\ntotalRandomBetterIterations : "
				+ totalRandomBetterIterations);
		summaryWriter.write("\nelvesUsed : " + elvesUsed);
		summaryWriter.write("\ntotalSameObjValInterations : "
				+ totalSameObjValInterations);
		summaryWriter.write("\ntotalSameObjValInterationsAccepted : "
				+ totalSameObjValInterationsAccepted);
		summaryWriter.write("\ntotalBestIterations : " + totalBestIterations);
		summaryWriter.write("\nbestAssignmentsElvesUsed : "
				+ bestAssignmentsElvesUsed);
		summaryWriter.write("\nbestAssignmentsObjVal : "
				+ bestAssignmentsObjVal);
		summaryWriter.write("\nbestAssignmentsMaxTime : "
				+ bestAssignmentsMaxTime);
		summaryWriter.write("\nbestAssignmentsMaxTimeElvdId : "
				+ bestAssignmentsMaxTimeElvdId);

		Elve[] testElves = new Elve[901];
		for (int i = 1; i < 901; i++)
			testElves[i] = new Elve(i);

		ObjectiveValueHelper.ObjectiveValueData testData = ObjectiveValueHelper
				.calculateObjectiveValue(testElves, bestAssignments);
		summaryWriter.write("\ntestObjVal : " + testData.val);
		summaryWriter.write("\ntestEndTime : " + testData.endTime);
		summaryWriter.write("\ntestElvesUsed : " + testData.elvesUsed);
		summaryWriter.write("\ntestEndTimeElveId : "
				+ testData.maxEndTimeElveId);
		summaryWriter.write("\nexp : 2");

		summaryWriter.close();

		OutputFileHelper outputFileHelper = new OutputFileHelper(outputFolder);
		outputFileHelper.writeAllAssignments(bestAssignments);
		outputFileHelper.done();

	}

	private static List<Toy>[] copyAssignments(List<Toy>[] assignments) {
		List<Toy>[] copy = new ArrayList[assignments.length];
		for (int i = 1; i < 901; i++) {
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

		return Math.exp((prevObj - newObj) / temp);
	}

}
