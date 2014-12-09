package util;

import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;

public class IndexedFeatureTrainer {

	public OnlineLogisticRegression trainByOnlineLogisticRegression(String file,int cardinality)
	{
		IndexedFeatureFileReader fileReader = new IndexedFeatureFileReader(file, cardinality);
		OnlineLogisticRegression model = new OnlineLogisticRegression();
		
		IndexedFeatureFileReader.VectorData vectorData;
		
		while((vectorData = fileReader.next())!=null)
		{
			model.train(vectorData.getClick(), vectorData.getVector());
		}
		
		return model;
			
	}
	
}
