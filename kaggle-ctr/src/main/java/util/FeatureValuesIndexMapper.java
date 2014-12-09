package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FeatureValuesIndexMapper {

	private static final String VALUE_INDEX_DELIM = "\t";
	private static final String FEATURE_VALUE_DELIM = ",";
	private Map<Integer, Map<String, Integer>> indicesMap = new HashMap<Integer, Map<String, Integer>>();
	private int currIndex = -1;
	private boolean considerNewValues = true;

	public int getFeatureValueIndex(int featureNumber, String value) {
		Map<String, Integer> valuesForFeature = indicesMap.get(featureNumber);
		if (valuesForFeature == null) {
			valuesForFeature = new HashMap<String, Integer>();
			indicesMap.put(featureNumber, valuesForFeature);
		}

		if (!valuesForFeature.containsKey(value)) {
			if (considerNewValues) {
				currIndex++;
				valuesForFeature.put(value, currIndex);
				return currIndex;
			} else {
				return -1;
			}
		} else
			return valuesForFeature.get(value);

	}

	public void stopConsideringValues() {
		considerNewValues = false;
	}

	public int readFromFile(String file) {
		int maxIndex = -1;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splits = line.split(VALUE_INDEX_DELIM);
				int index = Integer.parseInt(splits[1]);

				String[] colValSplits = splits[0].split(FEATURE_VALUE_DELIM);
				int col = Integer.parseInt(colValSplits[0]);
				String val = colValSplits[1];

				Map<String, Integer> mapForCol = indicesMap.get(col);
				if (mapForCol == null) {
					mapForCol = new HashMap<String, Integer>();
					indicesMap.put(col, mapForCol);
				}

				mapForCol.put(val, index);

				if (index > maxIndex)
					maxIndex = index;

			}

			this.currIndex = maxIndex;

			return maxIndex;
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public int writeToFile(String outputFile) {
		System.out.println("Total distinct values " + (currIndex + 1));

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					outputFile));
			Set<Map.Entry<Integer, Map<String, Integer>>> entrySet = indicesMap
					.entrySet();
			for (Map.Entry<Integer, Map<String, Integer>> entry : entrySet) {
				Integer column = entry.getKey();
				Map<String, Integer> mapForColumn = entry.getValue();
				Set<Map.Entry<String, Integer>> entrySetForColumn = mapForColumn
						.entrySet();
				for (Map.Entry<String, Integer> entryForColumn : entrySetForColumn) {
					String val = entryForColumn.getKey();
					int ind = entryForColumn.getValue();
					bufferedWriter.write(column + FEATURE_VALUE_DELIM + val
							+ VALUE_INDEX_DELIM + ind + "\n");
				}

			}
			bufferedWriter.close();
			return currIndex;
			/*
			 * ObjectOutputStream os = new ObjectOutputStream(new
			 * FileOutputStream(outputFile)); os.writeObject(indicesMap);
			 * os.close();
			 */
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
