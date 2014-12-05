package process;

import java.util.ArrayList;
import java.util.List;

import data.Feature;
import data.Field;

public class LRFirstApproach {
	
	public static void main(String args[])
	{
		List<Feature> features = new ArrayList<Feature>();
		features.add(new Feature(new Field[]{Field.BANNER_POS,Field.C15,Field.C16}));
		features.add(new Feature(new Field[]{Field.SITE_CATEGORY,Field.C15,Field.C16}));
		features.add(new Feature(new Field[]{Field.APP_CATEGORY,Field.C15,Field.C16}));
		features.add(new Feature(new Field[]{Field.DEVICE_TYPE,Field.C15,Field.C16}));
		features.add(new Feature(new Field[]{Field.C1,Field.C15,Field.C16}));
		
		features.add(new Feature(new Field[]{Field.BANNER_POS,Field.C18}));
		features.add(new Feature(new Field[]{Field.SITE_ID,Field.C18}));
		features.add(new Feature(new Field[]{Field.SITE_DOMAIN,Field.C18}));
		features.add(new Feature(new Field[]{Field.SITE_CATEGORY,Field.C18}));
		features.add(new Feature(new Field[]{Field.APP_ID,Field.C18}));
		features.add(new Feature(new Field[]{Field.APP_DOMAIN,Field.C18}));
		features.add(new Feature(new Field[]{Field.APP_CATEGORY,Field.C18}));
		features.add(new Feature(new Field[]{Field.DEVICE_MODEL,Field.C18}));
		features.add(new Feature(new Field[]{Field.DEVICE_TYPE,Field.C18}));
		features.add(new Feature(new Field[]{Field.C1,Field.C18}));
		
		features.add(new Feature(new Field[]{Field.BANNER_POS,Field.C19}));
		features.add(new Feature(new Field[]{Field.SITE_ID,Field.C19}));
		features.add(new Feature(new Field[]{Field.SITE_DOMAIN,Field.C19}));
		features.add(new Feature(new Field[]{Field.SITE_CATEGORY,Field.C19}));
		features.add(new Feature(new Field[]{Field.APP_ID,Field.C19}));
		features.add(new Feature(new Field[]{Field.APP_DOMAIN,Field.C19}));
		features.add(new Feature(new Field[]{Field.APP_CATEGORY,Field.C19}));
		features.add(new Feature(new Field[]{Field.DEVICE_MODEL,Field.C19}));
		features.add(new Feature(new Field[]{Field.DEVICE_TYPE,Field.C19}));
		features.add(new Feature(new Field[]{Field.C1,Field.C19}));
		
		features.add(new Feature(new Field[]{Field.BANNER_POS,Field.C20}));
		features.add(new Feature(new Field[]{Field.SITE_ID,Field.C20}));
		features.add(new Feature(new Field[]{Field.SITE_DOMAIN,Field.C20}));
		features.add(new Feature(new Field[]{Field.SITE_CATEGORY,Field.C20}));
		features.add(new Feature(new Field[]{Field.APP_ID,Field.C20}));
		features.add(new Feature(new Field[]{Field.APP_DOMAIN,Field.C20}));
		features.add(new Feature(new Field[]{Field.APP_CATEGORY,Field.C20}));
		features.add(new Feature(new Field[]{Field.DEVICE_MODEL,Field.C20}));
		features.add(new Feature(new Field[]{Field.DEVICE_TYPE,Field.C20}));
		features.add(new Feature(new Field[]{Field.C1,Field.C20}));
	}

}
