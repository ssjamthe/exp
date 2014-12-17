package util;

import java.util.List;

import org.apache.mahout.classifier.sgd.L2;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.classifier.sgd.UniformPrior;

import data.Feature;

public class IndexedFeatureTrainer {

	public OnlineLogisticRegression trainByOnlineLogisticRegression(String file,int cardinality)
	{
		IndexedFeatureFileReader fileReader = new IndexedFeatureFileReader(file, cardinality);
		OnlineLogisticRegression model = new OnlineLogisticRegression(2,cardinality+1,new UniformPrior());
		
		IndexedFeatureFileReader.VectorData vectorData;
		
		while((vectorData = fileReader.next())!=null)
		{
			model.train(vectorData.getClick(), vectorData.getVector());
		}
		
		return model;
			
	}
	
}
