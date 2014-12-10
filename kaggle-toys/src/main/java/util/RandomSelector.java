package util;

import java.util.TreeMap;

public class RandomSelector<K> {
	
	private TreeMap<Range,K> elements = new TreeMap<Range, K>();
	double lastEnd = 0;
	
	private static class Range implements Comparable<Range>
	{
		double start;
		double end;
		
		@Override
		public int compareTo(Range o) {
			
			if(o.start < this.start)
				return 1;
			else if (o.start >= this.end)
				return -1;
			else
				return 0;
			
		}	
	}
	
	public void addElement(K element,double weight)
	{
		Range range = new Range();
		
		range.start = lastEnd;
		range.end = lastEnd + weight;
		lastEnd = range.end;
		
		elements.put(range, element);
	}
	
	public K selectElement()
	{
		double rand = Math.random()*lastEnd;
		
		Range randRange = new Range();
		
		randRange.start = rand;
		
		return elements.get(randRange);
		
	}

}
