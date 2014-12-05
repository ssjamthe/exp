package data;

public class AdInstance {
	
	private String fields[];
	
	public void setFields(String line)
	{
		fields = line.split(",");
	}
	
	public String getFieldValue(Field field)
	{
		return fields[field.index];
	}

}
