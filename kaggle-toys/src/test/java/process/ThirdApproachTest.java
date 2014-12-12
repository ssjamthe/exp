package process;

import static org.junit.Assert.*;

import org.junit.Test;

public class ThirdApproachTest {

	@Test
	public void testCalculateAcceptanceProb() {
		
		double val = 100;
		int iter = 0;
		double c = 0.9999935;
		
		while(val > 0.1)
		{
			iter++;
			if(iter %10000 == 0)
				System.out.println(iter);
			
			val = val *c;
		}
		
		System.out.println(iter);
				
		
		System.out.println(ThirdApproach.calculateAcceptanceProb(1777, 1777, 0.2));
		
		System.out.println(ThirdApproach.acceptChange(1777, 1900, 200));
		
	}

}
