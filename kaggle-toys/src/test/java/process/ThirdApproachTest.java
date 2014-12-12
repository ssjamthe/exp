package process;

import static org.junit.Assert.*;

import org.junit.Test;

public class ThirdApproachTest {

	@Test
	public void testCalculateAcceptanceProb() {
		
		/*double val = 20;
		int iter = 0;
		double c = 0.9999935;
		
		while(val > 1)
		{
			iter++;
			if(iter %10000 == 0)
				System.out.println(iter);
			
			val = val *c;
		}
		
		System.out.println(iter);*/
				
		
		//System.out.println(ThirdApproach.calculateAcceptanceProb(1000, 12000*6, 100000));
		System.out.println(ThirdApproach.calculateAcceptanceProb(1000, 1020*6, 2300));
		
		//System.out.println(ThirdApproach.acceptChange(2000, 1000, 50));
		
	}

}
