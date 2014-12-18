package util;

import org.apache.mahout.classifier.AbstractVectorClassifier;

public class ModelEvaluator {

	public static class ModelEvaluationResult {
		int classifiedRight;
		int classifiedWrong;
		double accuracy;
		int positiveClassifiedRight;
		int positiveClassifiedWrong;
		double positiveAccuracy;
		int negativeClassifiedRight;
		int negativeClassifiedWrong;
		double negativeAccuracy;
		
		
		@Override
		public String toString() {
			return "ModelEvaluationResult [classifiedRight=" + classifiedRight
					+ ", classifiedWrong=" + classifiedWrong + ", accuracy="
					+ accuracy + ", positiveClassifiedRight="
					+ positiveClassifiedRight + ", positiveClassifiedWrong="
					+ positiveClassifiedWrong + ", positiveAccuracy="
					+ positiveAccuracy + ", negativeClassifiedRight="
					+ negativeClassifiedRight + ", negativeClassifiedWrong="
					+ negativeClassifiedWrong + ", negativeAccuracy="
					+ negativeAccuracy + "]";
		}
	}

	public ModelEvaluationResult eveluateIndexedFeatureClassifier(
			String inputFile, int cardinality,
			AbstractVectorClassifier classifier) {
		IndexedFeatureFileReader fileReader = new IndexedFeatureFileReader(
				inputFile, cardinality);

		int numEvaluated = 0;

		IndexedFeatureFileReader.VectorData vectorData;

		ModelEvaluationResult result = new ModelEvaluationResult();

		while ((vectorData = fileReader.next()) != null) {

			double predictedVal = classifier.classifyScalar(vectorData
					.getVector());
			if (predictedVal >= 0.5) {
				if (vectorData.getClick() == 0) {

					result.classifiedWrong++;
					result.negativeClassifiedWrong++;

				} else {

					result.classifiedRight++;
					result.positiveClassifiedRight++;

				}
			} else {
				if (vectorData.getClick() == 0) {
					result.classifiedRight++;
					result.negativeClassifiedRight++;

				} else {
					result.classifiedWrong++;
					result.positiveClassifiedWrong++;

				}
			}

			numEvaluated++;
			if (numEvaluated % 10000 == 0) {
				System.out.println("numEvaluated=" + numEvaluated);
			}
		}

		result.accuracy = ((double) result.classifiedRight)
				/ (result.classifiedWrong + result.classifiedRight);
		result.positiveAccuracy = ((double) result.positiveClassifiedRight)
				/ (result.positiveClassifiedWrong + result.positiveClassifiedRight);
		result.negativeAccuracy = ((double) result.negativeClassifiedRight)
				/ (result.negativeClassifiedWrong + result.negativeClassifiedRight);

		return result;
	}

}
