package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Toy;

public class ToysAssignmentReader {
	
	public static final String DELIM = ",";
	
	
	
	public static void main(String[] args)
	{
		System.out.println("Max memory" + Runtime.getRuntime().maxMemory());
		System.out.println("total memory" + Runtime.getRuntime().totalMemory());
		System.out.println("free memory" + Runtime.getRuntime().freeMemory());
		
		readAssignments("D:\\Kaggle\\HelpingSantasHelpers\\output\\firstSecondApproach\\2014_12_6_23_48_final\\part-r-00000");
		
		System.out.println("Max memory" + Runtime.getRuntime().maxMemory());
		System.out.println("total memory" + Runtime.getRuntime().totalMemory());
		System.out.println("free memory" + Runtime.getRuntime().freeMemory());
	}
	
	public static List<Toy>[] readAssignments(String file) {
		try {
			int count = 0;
			BufferedReader reader = new BufferedReader(new FileReader(file));
			List<Toy>[] assignments= new ArrayList[901];
			for(int i=1;i<901;i++)
			{
				assignments[i] = new ArrayList<Toy>();
			}
			
			ToysFileReader toysReader = new ToysFileReader();
			Map<String,Toy> allToys = toysReader.readAllToys();
			
			String line;
			while((line=reader.readLine())!=null)
			{
				String splits[] = line.split(DELIM);
				String toyId = splits[0];
				String elveId = splits[1];
				
				assignments[Integer.parseInt(elveId)].add(allToys.get(toyId));		
				
				count++;
				if(count % 10000 == 0)
				{
					System.out.println("Assignments read " + count);
				}
			}

			
			for(int i=1;i<901;i++)
			{
				List<Toy> toys = assignments[i];
				Collections.sort(toys);
			}
			
			return assignments;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	

}
