package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class ObjectiveValueHelperTest {

	@Test
	public void testCalculateObjectiveValue() {
		
		
		System.out.println(ObjectiveValueHelper.calculateObjectiveValue(275736589, 900));
		System.out.println(ObjectiveValueHelper.calculateObjectiveValue(10633+501, 899));
		
	}

}
