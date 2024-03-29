package process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.Elve;
import data.Toy;
import util.AssignmentsDistributionWriter;
import util.LinearProgrammingHelper;
import util.ObjectiveValueHelper;
import util.OutputFileHelper;
import util.SimulatedAnnealingHelper;
import util.ToysFileReader;

/*
 * Takes initial assignment in batch from LP and then seds to simulated annealing to get better assignments
 */
public class FifthApproach {

	private static final int BATCH_SIZE = 1000;
	private static final String outputDir = "D:/Kaggle/HelpingSantasHelpers/output/fifthApproach";
	private static final double MAX_TEMP = 200000;
	private static final double MIN_TEMP = 40000;
	private static final double COOLING_FACTOR = 0.999996;
	private static final double TOTALLY_RANDOM_WEIGHT = 20;
	private static final double WEIGHTED_RANDOM_WEIGHT = 30;
	private static final double ONLY_TO_ELVE_WEIGHTED_RANDOM_WEIGHT = 50;
	private static final double ACCEPT_SAME_OBJECTVE_PROB = 0.9;

	public static void main(String args[]) throws IOException {

		ToysFileReader toysReader = new ToysFileReader();
		ArrayList<Toy> toys = toysReader.readAllToysInList();
		ArrayList<Toy>[] assignments = new ArrayList[901];

		Elve elves[] = new Elve[901];
		for (int i = 1; i < 901; i++) {
			elves[i] = new Elve(i);
			assignments[i] = new ArrayList<Toy>();
		}

		int currStartToyIndex = 0;
		int iterationStep = 0;
		
		int maxEndTime = 0;
		int maxElveId = -1;

		LinearProgrammingHelper lpHelper = new LinearProgrammingHelper();
		SimulatedAnnealingHelper saHelper = new SimulatedAnnealingHelper(
				MAX_TEMP, MIN_TEMP, COOLING_FACTOR, TOTALLY_RANDOM_WEIGHT,
				WEIGHTED_RANDOM_WEIGHT, ONLY_TO_ELVE_WEIGHTED_RANDOM_WEIGHT,
				ACCEPT_SAME_OBJECTVE_PROB);

		while (currStartToyIndex < toys.size()) {
			int endIndex = currStartToyIndex + BATCH_SIZE;

			long lpStartTime = System.currentTimeMillis();
			List<Toy>[] newAssignments = lpHelper.optimize(elves, toys,
					currStartToyIndex, endIndex);
			long lpEndTime = System.currentTimeMillis();

			String iterationStepStr = Integer.toString(iterationStep);
			long saStartTime = System.currentTimeMillis();
			// modifies toys as per the new assignments
			newAssignments = saHelper.optimize(elves, newAssignments,outputDir + "/" + "sa_" + iterationStepStr , "Main step " + iterationStepStr);
			long saEndTime = System.currentTimeMillis();
			
			System.out.println("Done step " + iterationStepStr + " , lp time : " + (lpEndTime - lpStartTime) + " , sa time : " + (saEndTime - saStartTime));
			
			for (int i = 1; i < 901; i++) {
				assignments[i].addAll(newAssignments[i]);
				
				if(maxEndTime < elves[i].getLastJobFinishTime())
				{
					maxEndTime = elves[i].getLastJobFinishTime();
					maxElveId = i;
				}
			}

			iterationStep++;
			currStartToyIndex = currStartToyIndex + BATCH_SIZE;
						
		}
		
		AssignmentsDistributionWriter.writeAssignments(outputDir + "/" + "assignmentsDistribution.txt", assignments);
		
		ObjectiveValueHelper.ObjectiveValueData objData = ObjectiveValueHelper.calculateObjectiveValue(assignments);
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputDir + "/" + "summary.txt"));
		writer.write("\nfinal obj : " + objData.val + "\n");
		writer.write("\nfinal end time : " + objData.endTime + "\n");
		writer.write("\nfinal max end time elve id : " + objData.maxEndTimeElveId + "\n");
		writer.write("\nfinal elves used : " + objData.elvesUsed + "\n");
		writer.write("\ntest max end time : " + maxEndTime);
		writer.write("\ntest max end time elve id: " + maxElveId);
		
		writer.close();
		
		OutputFileHelper outputFileHelper = new OutputFileHelper(outputDir);
		outputFileHelper.writeAllAssignments(assignments);
		outputFileHelper.done();

	}

}
