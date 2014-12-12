package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomSelectorWithoutReplacementTest {

	@Test
	public void test() {

		RandomSelectorWithoutReplacement<String> selector = new RandomSelectorWithoutReplacement<String>();

		selector.addElement("1", 10.5);
		selector.addElement("2", 100.1);
		selector.addElement("3", 500.6);
		
		System.out.println(selector.selectElement());
	}

}
