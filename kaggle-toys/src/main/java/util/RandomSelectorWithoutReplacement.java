package util;

import java.util.Iterator;
import java.util.LinkedList;

public class RandomSelectorWithoutReplacement<K> {

	private LinkedList<Entry<K>> list = new LinkedList<Entry<K>>();
	double sumOfWeights = 0;

	private static class Entry<K> {
		private K element;
		double weight;
	}

	public void addElement(K element, double weight) {

		if (weight <= 0) {
			throw new IllegalStateException("weight cannot be <=0");
		}
		Entry<K> entry = new Entry<K>();
		entry.element = element;
		entry.weight = weight;

		sumOfWeights = sumOfWeights + weight;

		list.add(entry);
	}

	public K selectElement() {
		if (list.isEmpty())
			throw new IllegalStateException("Empty selector..");

		double rand = Math.random() * sumOfWeights;

		Iterator<Entry<K>> iter = list.iterator();
		double lastEnd = 0;

		while (iter.hasNext()) {
			Entry<K> entry = iter.next();
			double start = lastEnd;
			double end = lastEnd + entry.weight;
			if (rand >= start && rand < end) {
				iter.remove();
				sumOfWeights = sumOfWeights - entry.weight;
				return entry.element;
			}
			lastEnd = end;
		}

		throw new IllegalStateException("Should not reach here... rand : " + rand + ", sum of weights : " + lastEnd);

	}

}
