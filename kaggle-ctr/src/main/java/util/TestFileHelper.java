package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TestFileHelper {
	
	private static final String DELIM = ",";
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("D:\\Kaggle\\AvazuCtrPrediction\\test\\test.csv"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\Kaggle\\AvazuCtrPrediction\\test\\test_converted.csv"));
		
		String line;
		int lines=0;
		
		boolean header = true;
		while((line = br.readLine())!=null)
		{
			String splits[] = line.split(",");
			
			if(header)
				bw.write(splits[0] + DELIM + "click,");
			else
				bw.write(splits[0] + DELIM + "0,");
			for(int i=1;i<splits.length;i++)
			{
				bw.write(splits[i]);
				if(i < splits.length - 1)
					bw.write(",");
			}
			
			bw.write("\n");
			
			lines++;
			
			if(lines % 10000 == 0)
				System.out.println("Lines converted " + lines);
			
			header = false;
		}
		
		br.close();
		bw.close();
	}

}
