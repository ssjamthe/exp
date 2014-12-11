package util;

import java.util.Iterator;
import java.util.LinkedList;



public class RandomSelectorWithoutReplacement<K> {
	
	private LinkedList<Entry<K>> list = new LinkedList<Entry<K>>(); 
	double lastEnd = 0;
	
	private static class Entry<K>{
		private K element;
		double start;
		double end;
	}
	
	public void addElement(K element,double weight)
	{
		Entry<K> entry = new Entry<K>();
		entry.element = element;
		entry.start = lastEnd;
		entry.end = lastEnd + weight;
		
		lastEnd = entry.end;
		
		list.add(entry);
	}
	
	public K selectElement()
	{
		double rand = Math.random() * lastEnd;
		
		Iterator<Entry<K>> iter = list.iterator();
		
		while(iter.hasNext())
		{
			Entry<K> entry = iter.next();
			if(rand>=entry.start && rand<entry.end)
			{
				iter.remove();
				return entry.element;
			}
		}
		
		throw new IllegalStateException("Should not reach here...");
		
	}

}
