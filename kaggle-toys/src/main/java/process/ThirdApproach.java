package process;

import java.util.List;

import data.Toy;

import util.ToysAssignmentReader;

public class ThirdApproach {
	private static final String outputFolder = "D:/Kaggle/HelpingSantasHelpers/output/thirdApproach/";
	private static final String initialAssignmentsFile = "D:\\Kaggle\\HelpingSantasHelpers\\output\\firstSecondApproach\\2014_12_6_23_48_final\\part-r-00000";
	
	public static void main(String args[])
	{
		System.out.println("By third approach...");
		
		List<Toy>[] assignments = ToysAssignmentReader.readAssignments(initialAssignmentsFile);
	}

}
