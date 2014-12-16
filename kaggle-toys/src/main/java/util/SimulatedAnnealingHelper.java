package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.annotations.Beta;

import data.Elve;
import data.Toy;

public class SimulatedAnnealingHelper {

	private final double maxTemp;
	private final double minTemp;
	private final double coolingFactor;
	private final double totallyRandomWeight;
	private final double weightedRandomWeight;
	private final double onlyToElveWeightedRandomWeight;
	private final double acceptSameObjValProb;
	private static final int TOTALLY_RANDOM_STEP = 1;
	private static final int WEIGHTED_RANDOM_STEP = 2;
	private static final int ONLY_TO_ELVE_WEIGHTED_RANDOM_STEP = 3;

	public SimulatedAnnealingHelper(double maxTemp, double minTemp,
			double coolingFactor, double totallyRandomWeight,
			double weightedRandomWeight, double onlyToElveWeightedRandomWeight,
			double acceptSameObjValProb) {

		this.maxTemp = maxTemp;
		this.minTemp = minTemp;
		this.coolingFactor = coolingFactor;
		this.totallyRandomWeight = totallyRandomWeight;
		this.weightedRandomWeight = weightedRandomWeight;
		this.onlyToElveWeightedRandomWeight = onlyToElveWeightedRandomWeight;
		this.acceptSameObjValProb = acceptSameObjValProb;

	}

	public List<Toy>[] optimize(Elve[] elves, List<Toy>[] assignments,
			String logDir, String logMsg) {

		File logDirFile = new File(logDir);
		if (!logDirFile.exists()) {
			logDirFile.mkdir();
		}

		assignments = copyAssignments(assignments);

		ObjectiveValueHelper.ObjectiveValueData objData = ObjectiveValueHelper
				.calculateObjectiveValue(elves, assignments);

		BufferedWriter initialDataWriter;
		try {
			initialDataWriter = new BufferedWriter(new FileWriter(logDir
					+ "/initialInfo.txt"));

			initialDataWriter.write("initial obj vaue " + objData.val + "\n");
			initialDataWriter.write("initial elves used " + objData.elvesUsed
					+ "\n");
			initialDataWriter.write("initial max end time " + objData.endTime
					+ "\n");
			initialDataWriter.write("initial max end time elve id "
					+ objData.maxEndTimeElveId + "\n");
			initialDataWriter.close();

			AssignmentsDistributionWriter.writeAssignments(logDir
					+ "/initialAssignmentsDistribution.txt", assignments);

			BufferedWriter stepsWriter = new BufferedWriter(new FileWriter(
					logDir + "/StepsInfo.txt"));

			double temp = this.maxTemp;
			double minTemp = this.minTemp;
			double coolingFactor = this.coolingFactor;

			RandomSelectorWithoutReplacement<Integer> fromElveSelector = new RandomSelectorWithoutReplacement<Integer>();
			for (int i = 1; i < 901; i++) {
				fromElveSelector.addElement(i,
						(double) elves[i].getLastJobFinishTime());
			}

			RandomSelectorWithoutReplacement<Integer> toElveSelector = new RandomSelectorWithoutReplacement<Integer>();
			for (int i = 1; i < 901; i++) {
				toElveSelector.addElement(i,
						1.0 / elves[i].getLastJobFinishTime());
			}

			int iter = 0;
			int totalIterationsAccepted = 0;
			int totalRandomIterations = 0;
			int totalRandomAccepted = 0;
			int totalWeightedRandomIterations = 0;
			int totalWeightedRandomAccepted = 0;
			int totalOnlyToElveWeightedRandomIterations = 0;
			int totalOnlyToElveWeightedAccepted = 0;
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

			RandomSelectorWithoutReplacement<Integer> stepSelector = new RandomSelectorWithoutReplacement<Integer>();
			if (totallyRandomWeight != 0) {
				stepSelector.addElement(TOTALLY_RANDOM_STEP,
						totallyRandomWeight);
			}

			if (weightedRandomWeight != 0) {
				stepSelector.addElement(WEIGHTED_RANDOM_STEP,
						weightedRandomWeight);
			}

			if (onlyToElveWeightedRandomWeight != 0) {
				stepSelector.addElement(ONLY_TO_ELVE_WEIGHTED_RANDOM_STEP,
						onlyToElveWeightedRandomWeight);
			}

			Random random = new Random();

			while (temp > minTemp) {
				iter++;
				if (iter % 10000 == 0) {
					System.out.println("iter : " + iter + ", temp : " + temp
							+ "..." + logMsg);
				}

				int selectedStep = stepSelector.selectElement(true);

				int fromElveId;
				int toElveId;

				switch (selectedStep) {
				case TOTALLY_RANDOM_STEP:
					totalRandomIterations++;

					fromElveId = random.nextInt(900) + 1;
					toElveId = random.nextInt(900) + 1;
					while (toElveId == fromElveId) {
						toElveId = random.nextInt(900) + 1;
					}
					break;
				case WEIGHTED_RANDOM_STEP:
					totalWeightedRandomIterations++;

					fromElveId = fromElveSelector.selectElement();
					toElveId = toElveSelector.selectElement();
					break;
				case ONLY_TO_ELVE_WEIGHTED_RANDOM_STEP:
					totalOnlyToElveWeightedRandomIterations++;

					fromElveId = maxEndtimeEvleId;
					toElveId = toElveSelector.selectElement();
					while (toElveId == fromElveId) {
						toElveSelector.addElement(toElveId,
								elves[toElveId].getLastJobFinishTime());
						toElveId = toElveSelector.selectElement();
					}
					break;
				default:
					throw new RuntimeException("Should not reach here...");

				}

				if (fromElveId == toElveId) {
					numOfSameTransferElves++;
					// continue;
				}

				int numFromElveAssignments = assignments[fromElveId].size();

				boolean accept = false;
				boolean betterIteration = false;

				if (numFromElveAssignments > 0) {

					Toy toyToTransfer = assignments[fromElveId].get(random
							.nextInt(numFromElveAssignments));
					JobTransferHelper.JobTransferResult transferResult = JobTransferHelper
							.getJobTransferResult(fromElveId, toElveId,
									assignments[fromElveId],
									assignments[toElveId], toyToTransfer, elves);

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
							.calculateObjectiveValue(newMaxEndTime,
									newElvesUsed);

					if (newObjVal < objVal) {
						totalBetterIterations++;
						accept = true;
						betterIteration = true;

					} else if (newObjVal > objVal) {
						totalWorseIterations++;
						accept = acceptChange(objVal, newObjVal, temp);
						// stepsWriter.write("prevObjVal : " + objVal +
						// ", newObjVal : " + newObjVal + " temp : " + temp +
						// " accepted : " + accept + "\n");
					} else {
						totalSameObjValInterations++;
						if (Math.random() < acceptSameObjValProb) {
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

						if (newObjVal > objVal) {
							totalWorseIterationsAccepted++;
						}

						if (newObjVal < bestAssignmentsObjVal) {
							bestAssignments = copyAssignments(assignments);
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

				// System.out.println("to elve selector size : " +
				// toElveSelector.size());

				switch (selectedStep) {
				case TOTALLY_RANDOM_STEP:
					if (betterIteration) {
						totalRandomBetterIterations++;
					}
					if (accept) {
						totalRandomAccepted++;
					}
					break;
				case WEIGHTED_RANDOM_STEP:

					if (accept) {
						totalWeightedRandomAccepted++;
					}

					fromElveSelector.addElement(fromElveId,
							elves[fromElveId].getLastJobFinishTime());
					toElveSelector.addElement(toElveId,
							elves[toElveId].getLastJobFinishTime());
					break;
				case ONLY_TO_ELVE_WEIGHTED_RANDOM_STEP:

					if (accept) {
						totalOnlyToElveWeightedAccepted++;
					}

					toElveSelector.addElement(toElveId,
							elves[toElveId].getLastJobFinishTime());
					break;
				default:
					throw new RuntimeException("Should not reach here...");

				}

				temp = temp * coolingFactor;
			}

			stepsWriter.close();

			AssignmentsDistributionWriter.writeAssignments(logDir
					+ "assignmentsDistribution.txt", bestAssignments);

			BufferedWriter summaryWriter = new BufferedWriter(new FileWriter(
					logDir + "/summary.txt"));
			summaryWriter.write("\niter : " + iter);
			summaryWriter.write("\ntotalIterationsAccepted : "
					+ totalIterationsAccepted);
			summaryWriter.write("\ntotalRandomIterations : "
					+ totalRandomIterations);
			summaryWriter.write("\ntotalRandomAccepted : "
					+ totalRandomAccepted);
			summaryWriter.write("\ntotalWeightedRandomIterations : "
					+ totalWeightedRandomIterations);
			summaryWriter.write("\ntotalWeightedRandomAccepted : "
					+ totalWeightedRandomAccepted);
			summaryWriter.write("\ntotalOnlyToElveWeightedRandomIterations : "
					+ totalOnlyToElveWeightedRandomIterations);
			summaryWriter.write("\ntotalOnlyToElveWeightedAccepted : "
					+ totalOnlyToElveWeightedAccepted);
			summaryWriter.write("\ntotalWorseIterations : "
					+ totalWorseIterations);
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
			summaryWriter.write("\ntotalBestIterations : "
					+ totalBestIterations);
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

			return bestAssignments;

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

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
