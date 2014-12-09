package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.mahout.classifier.AbstractVectorClassifier;

public class IndexedFeatureClassifier {

	private static final String DELIM = ","; 
	public void classify(String inputFile, int cardinality,
			AbstractVectorClassifier classifier, String outputFile) {
		IndexedFeatureFileReader fileReader = new IndexedFeatureFileReader(
				inputFile, cardinality);

		IndexedFeatureFileReader.VectorData vectorData;

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					outputFile));

			while ((vectorData = fileReader.next()) != null) {

				double predictedVal = classifier.classifyScalar(vectorData
						.getVector());
				
				writer.write(vectorData.getAdId() + DELIM + predictedVal + "\n");

			}
			
			writer.close();

		} catch (IOException e) {		
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
