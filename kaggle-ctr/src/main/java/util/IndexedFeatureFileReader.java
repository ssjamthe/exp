package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.mahout.math.AbstractVector;
import org.apache.mahout.math.RandomAccessSparseVector;

public class IndexedFeatureFileReader {

	private static final String DELIM = ",";
	private BufferedReader bufferedReader;
	private int cardinality;

	public static class VectorData {
		private String adId;
		private AbstractVector vector;
		private int click;

		public AbstractVector getVector() {
			return vector;
		}

		public int getClick() {
			return click;
		}

		public String getAdId() {
			return adId;
		}
		
	}

	public IndexedFeatureFileReader(String file, int cardinality) {
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			this.cardinality = cardinality;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public VectorData next() {
		String line;

		try {
			line = bufferedReader.readLine();
			if (line == null)
				return null;

			return createVectorData(line);

		} catch (IOException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private VectorData createVectorData(String line) {
		RandomAccessSparseVector vector = new RandomAccessSparseVector(
				cardinality + 1);
		vector.set(cardinality, 1);
		String splits[] = line.split(DELIM);
		for (int i = 1; i < splits.length - 1; i++) {
			int ind = Integer.parseInt(splits[i]);
			if (ind >= 0) {
				vector.set(ind, 1);
			}

		}

		VectorData vectorData = new VectorData();
		vectorData.click = Integer.parseInt(splits[splits.length - 1]);
		vectorData.vector = vector;
		vectorData.adId = splits[0];

		return vectorData;
	}

}
