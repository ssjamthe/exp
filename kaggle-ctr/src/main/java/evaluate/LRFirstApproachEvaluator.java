package evaluate;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;

import util.ModelEvaluator;

public class LRFirstApproachEvaluator {
	
	private static final String modelFile = "D:\\Kaggle\\AvazuCtrPrediction\\processed\\firstApproach\\model.ser";
	private static final String trainingIndicesFile = "D:\\Kaggle\\AvazuCtrPrediction\\processed\\firstApproach\\trainFile.txt";
	private static final String evaluationResultFile = "D:\\Kaggle\\AvazuCtrPrediction\\processed\\firstApproach\\evaluation.txt";

	public static void main(String args[]) throws FileNotFoundException, IOException {
		
		OnlineLogisticRegression model = new OnlineLogisticRegression();
		model.readFields(new DataInputStream(new FileInputStream(modelFile)));
		
		ModelEvaluator modelEveluator = new ModelEvaluator();
		ModelEvaluator.ModelEvaluationResult result = modelEveluator.eveluateIndexedFeatureClassifier(trainingIndicesFile, 3889843, model);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(evaluationResultFile));
		writer.write(result.toString());
		writer.close();
		

	}

}
