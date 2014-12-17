package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class AdsFileSplitter {

	private static final String mainTrainFile = "D:\\Kaggle\\AvazuCtrPrediction\\train\\train.csv";
	private static final String outParentDir = "D:\\Kaggle\\AvazuCtrPrediction\\train";
	private static final double heldOutProb = 0.2;
	private static final String newLineStr = System
			.getProperty("line.separator");

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Calendar calendar = Calendar.getInstance();
		String name = calendar.get(Calendar.YEAR) + "_"
				+ (calendar.get(Calendar.MONTH) + 1) + "_"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "_"
				+ calendar.get(Calendar.HOUR_OF_DAY) + "_"
				+ calendar.get(Calendar.MINUTE);
		String outDir = outParentDir + "\\" + name;
		File outDirFile = new File(outDir);
		outDirFile.mkdir();

		int linesProcessed = 0;

		String trainingSetFileName = outDir + "\\" + "trainingSet.txt";
		String heldOutSetFileName = outDir + "\\" + "heldOutSet.txt";

		BufferedWriter triningSetwriter = new BufferedWriter(new FileWriter(
				trainingSetFileName));
		BufferedWriter heldOutSetWriter = new BufferedWriter(new FileWriter(
				heldOutSetFileName));

		BufferedReader reader = new BufferedReader(
				new FileReader(mainTrainFile));
		String line;
		while ((line = reader.readLine()) != null) {
			double rand = Math.random();
			if (rand < heldOutProb) {
				heldOutSetWriter.write(line);
				heldOutSetWriter.write(newLineStr);

			} else {
				triningSetwriter.write(line);
				triningSetwriter.write(newLineStr);
			}

			linesProcessed++;
			if (linesProcessed % 10000 == 0) {
				System.out.println("Lines processed : " + linesProcessed);
			}
		}

		reader.close();
		triningSetwriter.close();
		heldOutSetWriter.close();

	}

}
