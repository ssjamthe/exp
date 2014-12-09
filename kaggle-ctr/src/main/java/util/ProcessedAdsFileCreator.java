package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import data.AdInstance;
import data.Feature;
import data.Field;

public class ProcessedAdsFileCreator {
	private static final String FEATURE_DELIM = ",";

	public int createFile(List<Feature> features, String inputFile,
			String outputFile, String indicesMapFile) {
		AdsFileReader fileReader = new AdsFileReader(inputFile);
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					outputFile));
			FeatureValuesIndexMapper fim = new FeatureValuesIndexMapper();
			

			AdInstance ad = new AdInstance();
			while (fileReader.readNextAdInstance(ad)) {
				writeAdByIndex(features,ad,fim,bufferedWriter);
			}

			bufferedWriter.close();

			return fim.writeToFile(indicesMapFile);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private void writeAdByIndex(List<Feature> features,AdInstance ad,
			FeatureValuesIndexMapper fim, BufferedWriter writer) throws IOException {
		
		StringBuilder outputLine = new StringBuilder();
		outputLine.append(ad.getFieldValue(Field.ID) + FEATURE_DELIM);
		int ind = 1;
		for (Feature feature : features) {
			String value = feature.getFeatureValue(ad);

			int valInd = fim.getFeatureValueIndex(ind, value);
			outputLine.append(valInd);

			outputLine.append(FEATURE_DELIM);
			ind++;
		}

		writer.write(outputLine + ad.getFieldValue(Field.CLICK)
				+ "\n");

	}

	public void createFileFromIndices(List<Feature> features,String inputFile, String outputFile,
			String indexFile) {
		AdsFileReader fileReader = new AdsFileReader(inputFile);
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					outputFile));
			FeatureValuesIndexMapper fim = new FeatureValuesIndexMapper();
			fim.readFromFile(indexFile);
			fim.stopConsideringValues();

			AdInstance ad = new AdInstance();
			while (fileReader.readNextAdInstance(ad)) {
				writeAdByIndex(features,ad,fim,bufferedWriter);
			}

			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
