package process;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ThirdApproachTest {

	@Test
	public void testCalculateAcceptanceProb() {
		
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		
		list.remove("2");
		
		System.out.println(list);
		
		System.out.println(list.get(1));
		
		list.add(1, "20");

		System.out.println(list);
		
		System.out.println(list.get(2));
		System.out.println(list.get(3));
		
		double val = 200000;
		double minVal = 40000;
		int iter = 0;
		//double c = 0.9999935;
		double c = 0.999996;
		
		while(val > minVal)
		{
			iter++;
			if(iter %10000 == 0)
				System.out.println(iter);
			
			val = val *c;
		}
		
		System.out.println("iters : " + iter);
				
		
		System.out.println(ThirdApproach.calculateAcceptanceProb(1000, 30000*6, 200000));
		System.out.println(ThirdApproach.calculateAcceptanceProb(1000, 20000*6, 40000));
		
		//System.out.println(ThirdApproach.acceptChange(2000, 1000, 50));
		
	}

}
