package classification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TrainFileCreator {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\Kaggle\\Tradeshift\\processeprocessedTrain.csv"));
		BufferedReader br1 = new BufferedReader(new FileReader("D:\\Kaggle\\Tradeshift\\train.csv\\train.csv"));
		BufferedReader br2 = new BufferedReader(new FileReader("D:\\Kaggle\\Tradeshift\\trainLabels.csv\\trainLabels.csv"));

		int lines = 0;
		String line1;
		while((line1=br1.readLine())!=null)
		{
			String line2 = br2.readLine();
			
			bw.write(line1 + "," + line2 + "\n");
			
			if(lines % 500 == 0)
				System.out.println("lines processed " + lines);
			
			lines++;
		}
		
		bw.close();
		br1.close();
		br2.close();
	}

}
