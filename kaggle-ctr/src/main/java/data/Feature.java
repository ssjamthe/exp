package data;

import java.util.Map;

public class Feature {
	
	private static final String DELIM = ":";
	private final Field[] fields;
	
	public Feature(Field[] fields)
	{
		this.fields = fields;
	}
	
	public String getFeatureValue(Map<Field,String> values)
	{
		StringBuilder sb = new StringBuilder();
		for(Field field : fields)
		{
			
			sb.append(values.get(field) + DELIM);
		}
		
		return sb.toString();
	}
	
	public String getFeatureValue(AdInstance ad)
	{
		StringBuilder sb = new StringBuilder();
		int ind = 1;
		for(Field field : fields)
		{
			sb.append(ad.getFieldValue(field));
			if(ind < fields.length)
				sb.append(DELIM);
			ind++;
		}
		
		return sb.toString();
	}

}
