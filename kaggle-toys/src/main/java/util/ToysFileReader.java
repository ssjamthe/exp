package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import data.Toy;

public class ToysFileReader {

	private BufferedReader bufferedReader;
	private int toysRead = 0;

	public ToysFileReader() {
		try {
			bufferedReader = new BufferedReader(
					new FileReader(
							"D:\\Kaggle\\HelpingSantasHelpers\\toys_rev2\\toys_rev2.csv"));
			bufferedReader.readLine();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean readNextToy(Toy toy) {
		String line;
		try {
			line = bufferedReader.readLine();
			if (line == null)
				return false;

			populateToy(line, toy);
			toysRead++;
			
			if(toysRead % 10000 == 0)
			{
				System.out.println("Toys read = " + toysRead);
			}
			
			return true;

		} catch (IOException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private static void populateToy(String toyStr, Toy toy) {
		String[] lineSplits = toyStr.split(",");

		toy.setValues(lineSplits[0], lineSplits[1],
				Integer.parseInt(lineSplits[2]));

	}

}
