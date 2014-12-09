package process;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;

import util.AdsFileReader;
import util.IndexedFeatureClassifier;
import util.IndexedFeatureTrainer;
import util.ProcessedAdsFileCreator;

import data.Feature;
import data.Field;

public class LRFirstApproach {

	public static void main(String args[]) {
		String trainingFile = "D:\\Kaggle\\AvazuCtrPrediction\\train\\demo.csv";
		String trainingIndicesFile = "D:\\Kaggle\\AvazuCtrPrediction\\processed\\firstApproach\\trainFile.txt";
		String indicesFile = "D:\\Kaggle\\AvazuCtrPrediction\\processed\\firstApproach\\indices.txt";
		String modelFile = "D:\\Kaggle\\AvazuCtrPrediction\\processed\\firstApproach\\model.ser";
		String processedTestFile = "D:\\Kaggle\\AvazuCtrPrediction\\processed\\firstApproach\\test.txt";
		String inputTestFile = "D:\\Kaggle\\AvazuCtrPrediction\\test\\head_test_converted.csv";
		String predictedOutputFile = "D:\\Kaggle\\AvazuCtrPrediction\\test\\predicted.csv"; 

		List<Feature> features = new ArrayList<Feature>();
		features.add(new Feature(new Field[] { Field.BANNER_POS, Field.C15,
				Field.C16 }));
		features.add(new Feature(new Field[] { Field.SITE_CATEGORY, Field.C15,
				Field.C16 }));
		features.add(new Feature(new Field[] { Field.APP_CATEGORY, Field.C15,
				Field.C16 }));
		features.add(new Feature(new Field[] { Field.DEVICE_TYPE, Field.C15,
				Field.C16 }));
		features.add(new Feature(new Field[] { Field.C1, Field.C15, Field.C16 }));

		features.add(new Feature(new Field[] { Field.BANNER_POS, Field.C18 }));
		features.add(new Feature(new Field[] { Field.SITE_ID, Field.C18 }));
		features.add(new Feature(new Field[] { Field.SITE_DOMAIN, Field.C18 }));
		features.add(new Feature(new Field[] { Field.SITE_CATEGORY, Field.C18 }));
		features.add(new Feature(new Field[] { Field.APP_ID, Field.C18 }));
		features.add(new Feature(new Field[] { Field.APP_DOMAIN, Field.C18 }));
		features.add(new Feature(new Field[] { Field.APP_CATEGORY, Field.C18 }));
		features.add(new Feature(new Field[] { Field.DEVICE_MODEL, Field.C18 }));
		features.add(new Feature(new Field[] { Field.DEVICE_TYPE, Field.C18 }));
		features.add(new Feature(new Field[] { Field.C1, Field.C18 }));

		features.add(new Feature(new Field[] { Field.BANNER_POS, Field.C19 }));
		features.add(new Feature(new Field[] { Field.SITE_ID, Field.C19 }));
		features.add(new Feature(new Field[] { Field.SITE_DOMAIN, Field.C19 }));
		features.add(new Feature(new Field[] { Field.SITE_CATEGORY, Field.C19 }));
		features.add(new Feature(new Field[] { Field.APP_ID, Field.C19 }));
		features.add(new Feature(new Field[] { Field.APP_DOMAIN, Field.C19 }));
		features.add(new Feature(new Field[] { Field.APP_CATEGORY, Field.C19 }));
		features.add(new Feature(new Field[] { Field.DEVICE_MODEL, Field.C19 }));
		features.add(new Feature(new Field[] { Field.DEVICE_TYPE, Field.C19 }));
		features.add(new Feature(new Field[] { Field.C1, Field.C19 }));

		features.add(new Feature(new Field[] { Field.BANNER_POS, Field.C20 }));
		features.add(new Feature(new Field[] { Field.SITE_ID, Field.C20 }));
		features.add(new Feature(new Field[] { Field.SITE_DOMAIN, Field.C20 }));
		features.add(new Feature(new Field[] { Field.SITE_CATEGORY, Field.C20 }));
		features.add(new Feature(new Field[] { Field.APP_ID, Field.C20 }));
		features.add(new Feature(new Field[] { Field.APP_DOMAIN, Field.C20 }));
		features.add(new Feature(new Field[] { Field.APP_CATEGORY, Field.C20 }));
		features.add(new Feature(new Field[] { Field.DEVICE_MODEL, Field.C20 }));
		features.add(new Feature(new Field[] { Field.DEVICE_TYPE, Field.C20 }));
		features.add(new Feature(new Field[] { Field.C1, Field.C20 }));

		features.add(new Feature(new Field[] { Field.BANNER_POS, Field.C21 }));
		features.add(new Feature(new Field[] { Field.SITE_ID, Field.C21 }));
		features.add(new Feature(new Field[] { Field.SITE_DOMAIN, Field.C21 }));
		features.add(new Feature(new Field[] { Field.SITE_CATEGORY, Field.C21 }));
		features.add(new Feature(new Field[] { Field.APP_ID, Field.C21 }));
		features.add(new Feature(new Field[] { Field.APP_DOMAIN, Field.C21 }));
		features.add(new Feature(new Field[] { Field.APP_CATEGORY, Field.C21 }));
		features.add(new Feature(new Field[] { Field.DEVICE_MODEL, Field.C21 }));
		features.add(new Feature(new Field[] { Field.DEVICE_TYPE, Field.C21 }));
		features.add(new Feature(new Field[] { Field.C1, Field.C21 }));

		features.add(new Feature(new Field[] { Field.BANNER_POS, Field.C17 }));
		features.add(new Feature(new Field[] { Field.SITE_ID, Field.C17 }));
		features.add(new Feature(new Field[] { Field.SITE_DOMAIN, Field.C17 }));
		features.add(new Feature(new Field[] { Field.SITE_CATEGORY, Field.C17 }));
		features.add(new Feature(new Field[] { Field.APP_ID, Field.C17 }));
		features.add(new Feature(new Field[] { Field.APP_DOMAIN, Field.C17 }));
		features.add(new Feature(new Field[] { Field.APP_CATEGORY, Field.C17 }));
		features.add(new Feature(new Field[] { Field.DEVICE_MODEL, Field.C17 }));
		features.add(new Feature(new Field[] { Field.DEVICE_TYPE, Field.C17 }));
		features.add(new Feature(new Field[] { Field.C1, Field.C17 }));

		features.add(new Feature(new Field[] { Field.C14 }));
		features.add(new Feature(new Field[] { Field.C15 }));
		features.add(new Feature(new Field[] { Field.C16 }));
		features.add(new Feature(new Field[] { Field.C17 }));
		features.add(new Feature(new Field[] { Field.C18 }));
		features.add(new Feature(new Field[] { Field.C19 }));
		features.add(new Feature(new Field[] { Field.C20 }));
		features.add(new Feature(new Field[] { Field.C21 }));
		features.add(new Feature(new Field[] { Field.C1 }));
		features.add(new Feature(new Field[] { Field.BANNER_POS }));
		features.add(new Feature(new Field[] { Field.SITE_ID }));
		features.add(new Feature(new Field[] { Field.SITE_DOMAIN }));
		features.add(new Feature(new Field[] { Field.SITE_CATEGORY }));
		features.add(new Feature(new Field[] { Field.APP_ID }));
		features.add(new Feature(new Field[] { Field.APP_DOMAIN }));
		features.add(new Feature(new Field[] { Field.APP_CATEGORY }));
		features.add(new Feature(new Field[] { Field.DEVICE_ID }));
		features.add(new Feature(new Field[] { Field.DEVICE_MODEL }));
		features.add(new Feature(new Field[] { Field.DEVICE_TYPE }));
		features.add(new Feature(new Field[] { Field.DEVICE_CONN_TYPE }));

		ProcessedAdsFileCreator trianFileCreator = new ProcessedAdsFileCreator();
		int maxIndex = trianFileCreator.createFile(features,trainingFile,
				trainingIndicesFile, indicesFile);

		IndexedFeatureTrainer trainer = new IndexedFeatureTrainer();
		OnlineLogisticRegression model = trainer
				.trainByOnlineLogisticRegression(trainingIndicesFile,
						maxIndex + 1);
		try {
			model.write(new ObjectOutputStream(new FileOutputStream(modelFile)));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		trianFileCreator.createFileFromIndices(features, inputTestFile, processedTestFile, indicesFile);
		
		IndexedFeatureClassifier classifer = new IndexedFeatureClassifier();
		classifer.classify(processedTestFile, maxIndex + 1, model, predictedOutputFile);
		
		

	}

}
