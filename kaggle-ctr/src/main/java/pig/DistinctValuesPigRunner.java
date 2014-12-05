package pig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.pig.ExecType;
import org.apache.pig.PigServer;
import org.apache.pig.backend.executionengine.ExecException;

public class DistinctValuesPigRunner {
	
	public static void main(String args[]) throws IOException
	{
		PigServer pigServer = new PigServer(ExecType.LOCAL);
		
		pigServer.registerScript("D:\\Kaggle\\gitrepo\\Kaggle\\kaggle-ctr\\src\\main\\pig\\distinctVals.pig");
	}

}
