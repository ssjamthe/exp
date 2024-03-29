package process;

import java.util.Comparator;
import java.util.PriorityQueue;

import data.Elve;
import data.Toy;
import util.OutputFileHelper;
import util.ToysFileReader;

public class FirstApproach {
	
	private static class ElveComparator implements Comparator<Elve>
	{

		public int compare(Elve o1, Elve o2) {
			double firstVal = o1.getRating()/o1.getNextAvailableTime();
			double secondVal = o2.getRating()/o2.getNextAvailableTime();
			
			
			return firstVal > secondVal ? -1:(firstVal<secondVal)?1:(o1.getId() < o2.getId())?-1:1;
		}
		
	}
	
	public static void main(String args[])
	{
		
		PriorityQueue<Elve> queue = new PriorityQueue<Elve>(900,new ElveComparator());
		for(int i=1;i<=900;i++)
		{
			queue.offer(new Elve(i));
		}
		
		ToysFileReader toysReader = new ToysFileReader();
		
		OutputFileHelper outputHelper = new OutputFileHelper("D:/Kaggle/HelpingSantasHelpers/output/firstSecondApproach/");
		
		
		Toy toy = new Toy();
		while(toysReader.readNextToy(toy))
		{
			Elve elve = queue.poll();
			
			Elve.WorkInfo workInfo = elve.work(toy);
			
			queue.offer(elve);
			
			outputHelper.write(toy.getToyId(), elve.getId(), workInfo.getStartTime(), workInfo.getDuration());		
			
		}
		
		outputHelper.done();
	}

}
