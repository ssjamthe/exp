package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import data.AdInstance;


public class AdsFileReader {

	private BufferedReader bufferedReader;
	private long adsRead = 0;
	
	public AdsFileReader(String file)
	{
		try {
			bufferedReader = new BufferedReader(
					new FileReader(
							file));
			bufferedReader.readLine();
			adsRead++;
			if(adsRead % 10000 == 0)
			{
				System.out.println("Ads read " + adsRead);
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public boolean readNextAdInstance(AdInstance ad) {
		String line;
		try {
			line = bufferedReader.readLine();
			if (line == null)
				return false;

			ad.setFields(line);
			adsRead++;
			
			if(adsRead % 10000 == 0)
			{
				System.out.println("Ads read = " + adsRead);
			}
			
			return true;

		} catch (IOException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
