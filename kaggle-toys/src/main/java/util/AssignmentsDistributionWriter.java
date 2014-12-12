package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import data.Toy;

public class AssignmentsDistributionWriter {

	private static final String DELIM = ",";

	public static void writeAssignments(String file, List<Toy>[] assignments) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
					file));
			
			for(int i=1;i<901;i++)
			{
				List<Toy> elveAssignments = assignments[i];
				bufferedWriter.write(i+DELIM+elveAssignments.size()+"\n");
			}
			
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to write assignments to file "
					+ file);
		}
	}

}
