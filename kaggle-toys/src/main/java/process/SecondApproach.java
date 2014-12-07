package process;


import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import util.OutputFileHelper;
import util.ToysFileReader;


import data.Elve;
import data.Toy;

public class SecondApproach {
	
	public static void main(String args[])
	{
		System.out.println("By second approach...");
		List<Elve> elveList = new ArrayList<Elve>(900);
		for(int i=1;i<=900;i++)
		{
			elveList.add(new Elve(Integer.toString(i)));
		}
		
		
		ToysFileReader toysReader = new ToysFileReader();
		
		OutputFileHelper outputHelper = new OutputFileHelper();
		
		
		Toy toy = new Toy();
		
		while(toysReader.readNextToy(toy))
		{
			Elve bestElve = elveList.get(0);
			int bestEndWorkingTime = bestElve.calculateFinishTime(toy);
			for(Elve elve : elveList)
			{
				int endWorkingTime = elve.calculateFinishTime(toy);
				if(endWorkingTime < bestEndWorkingTime)
				{
					bestElve = elve;
					bestEndWorkingTime = endWorkingTime;
				}
			}
			
			Elve.WorkInfo workInfo = bestElve.work(toy);
			
			
			outputHelper.write(toy.getToyId(), bestElve.getId(), workInfo.getStartTime(), workInfo.getDuration());		
			
		}
		
		outputHelper.done();
		
	}

}
