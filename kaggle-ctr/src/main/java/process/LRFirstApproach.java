package process;

import java.util.ArrayList;
import java.util.List;

import util.AdsFileReader;
import util.ProcessedTraningFileCreator;

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
		
		features.add(new Feature(new Field[]{Field.BANNER_POS,Field.C21}));
		features.add(new Feature(new Field[]{Field.SITE_ID,Field.C21}));
		features.add(new Feature(new Field[]{Field.SITE_DOMAIN,Field.C21}));
		features.add(new Feature(new Field[]{Field.SITE_CATEGORY,Field.C21}));
		features.add(new Feature(new Field[]{Field.APP_ID,Field.C21}));
		features.add(new Feature(new Field[]{Field.APP_DOMAIN,Field.C21}));
		features.add(new Feature(new Field[]{Field.APP_CATEGORY,Field.C21}));
		features.add(new Feature(new Field[]{Field.DEVICE_MODEL,Field.C21}));
		features.add(new Feature(new Field[]{Field.DEVICE_TYPE,Field.C21}));
		features.add(new Feature(new Field[]{Field.C1,Field.C21}));
		
		features.add(new Feature(new Field[]{Field.BANNER_POS,Field.C17}));
		features.add(new Feature(new Field[]{Field.SITE_ID,Field.C17}));
		features.add(new Feature(new Field[]{Field.SITE_DOMAIN,Field.C17}));
		features.add(new Feature(new Field[]{Field.SITE_CATEGORY,Field.C17}));
		features.add(new Feature(new Field[]{Field.APP_ID,Field.C17}));
		features.add(new Feature(new Field[]{Field.APP_DOMAIN,Field.C17}));
		features.add(new Feature(new Field[]{Field.APP_CATEGORY,Field.C17}));
		features.add(new Feature(new Field[]{Field.DEVICE_MODEL,Field.C17}));
		features.add(new Feature(new Field[]{Field.DEVICE_TYPE,Field.C17}));
		features.add(new Feature(new Field[]{Field.C1,Field.C17}));
		
		features.add(new Feature(new Field[]{Field.C14}));
		features.add(new Feature(new Field[]{Field.C15}));
		features.add(new Feature(new Field[]{Field.C16}));
		features.add(new Feature(new Field[]{Field.C17}));
		features.add(new Feature(new Field[]{Field.C18}));
		features.add(new Feature(new Field[]{Field.C19}));
		features.add(new Feature(new Field[]{Field.C20}));
		features.add(new Feature(new Field[]{Field.C21}));
		features.add(new Feature(new Field[]{Field.C1}));
		features.add(new Feature(new Field[]{Field.BANNER_POS}));
		features.add(new Feature(new Field[]{Field.SITE_ID}));
		features.add(new Feature(new Field[]{Field.SITE_DOMAIN}));
		features.add(new Feature(new Field[]{Field.SITE_CATEGORY}));
		features.add(new Feature(new Field[]{Field.APP_ID}));
		features.add(new Feature(new Field[]{Field.APP_DOMAIN}));
		features.add(new Feature(new Field[]{Field.APP_CATEGORY}));
		features.add(new Feature(new Field[]{Field.DEVICE_ID}));
		features.add(new Feature(new Field[]{Field.DEVICE_MODEL}));
		features.add(new Feature(new Field[]{Field.DEVICE_TYPE}));
		features.add(new Feature(new Field[]{Field.DEVICE_CONN_TYPE}));
		
		ProcessedTraningFileCreator trianFileCreator = new ProcessedTraningFileCreator();
		trianFileCreator.createFile(features, "D:\\Kaggle\\AvazuCtrPrediction\\processed\\firstApproach\\trainFile.txt","D:\\Kaggle\\AvazuCtrPrediction\\processed\\firstApproach\\indices.txt");
		
		
	}

}
