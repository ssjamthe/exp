package classification;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD;

public class OnlineLogisticRegression {

	public static void main(String args[]) {
		String logFile = "D:\\Spark\\spark-1.1.0\\README.md"; // Should be some
																// file on your
																// system
		SparkConf conf = new SparkConf().setAppName("Simple Application");
		JavaSparkContext sc = new JavaSparkContext(conf);

		final Set<Integer> categorical = new HashSet<Integer>();
		categorical.add(3);
		categorical.add(4);
		categorical.add(34);
		categorical.add(35);
		categorical.add(61);
		categorical.add(64);
		categorical.add(65);
		categorical.add(91);
		categorical.add(94);
		categorical.add(95);

		Map<Integer, Set<String>> distictValues = new HashMap<Integer, Set<String>>();
		for (Integer ind : categorical) {
			distictValues.put(ind, new HashSet<String>());
		}

		final Set<Map.Entry<Integer, Set<String>>> entrySet = distictValues
				.entrySet();

		JavaRDD<String[]> lines = sc.textFile(
				"D:\\Kaggle\\Tradeshift\\processeprocessedTrain.csv").map(
				new Function<String, String[]>() {
					public String[] call(String line) {
						String[] splits = line.split(",");

						for (Map.Entry<Integer, Set<String>> entry : entrySet) {
							Integer ind = entry.getKey();
							Set<String> valSet = entry.getValue();

							String val = splits[ind + 1];
							if (val.isEmpty())
								val = "NA";

							valSet.add(val);
						}

						return splits;						
					}
				});

		for (Map.Entry<Integer, Set<String>> entry : entrySet) {
			System.out.println("Distict vals for " + entry.getKey() + " are " + entry.getValue());
		}
		
		LogisticRegressionWithSGD lr = new LogisticRegressionWithSGD();

	}

}
