package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pig.ExecType;
import org.apache.pig.PigServer;

import data.Elve;
import data.Toy;

public class OutputFileHelper {
	private static final String pigScriptPath = "E:\\workspace\\java\\eclipse\\kaggle-toys\\src\\main\\resources\\pig\\finalOutputFile.pig";
	private BufferedWriter bufferedWriter;
	private String fileName;
	
	
	public OutputFileHelper(String outputFolder) {
		Calendar calendar = Calendar.getInstance();
		String name = calendar.get(Calendar.YEAR) + "_" + (calendar.get(Calendar.MONTH) + 1) + "_" + calendar.get(Calendar.DAY_OF_MONTH) + "_" +calendar.get(Calendar.HOUR_OF_DAY) + "_" +calendar.get(Calendar.MINUTE);
		fileName = outputFolder + name;
		
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(fileName));
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void write(String toyId,int elfId,int startTime,int duration)
	{
		try {
			bufferedWriter.write(toyId + "," + elfId + "," + TimeHelper.convertToString(startTime) + "," + duration + "," + (startTime+duration) + "\n");
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			throw new RuntimeException(e);
		}
	}
	
	public void writeAllAssignments(List<Toy>[] assignments)
	{
		for(int i=1;i<901;i++)
		{
			Elve elve = new Elve(i);
			List<Toy> toyAssignments = assignments[i];
			
			for(Toy toy : toyAssignments)
			{
				Elve.WorkInfo workInfo = elve.work(toy);
				write(toy.getToyId(),i,workInfo.getStartTime(),workInfo.getDuration());
			}
		}
	}
	
	public void done()
	{
		try {
			bufferedWriter.close();
			PigServer pigServer = new PigServer(ExecType.LOCAL);
			Map<String,String> map = new HashMap<String,String>();
			map.put("inputFile", fileName);
			map.put("outputFile", fileName + "_final");
			pigServer.registerScript(pigScriptPath,map);
			
			/*pigServer.registerQuery("data = LOAD '" + fileName + "' USING PigStorage(',');");
			pigServer.registerQuery("orderedData = ORDER data BY $5;");
			pigServer.registerQuery("finalData = FOREACH orderedData GENERATE data$1,data$2,data$3,data$4;");
			
			pigServer.store("finalData", fileName + "_final");*/
			
		} catch (IOException e) {
			e.printStackTrace();
			
			throw new RuntimeException(e);
		}
	}

}
