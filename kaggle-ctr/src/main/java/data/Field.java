package data;

public enum Field {
	
	ID(0,"No File"),
	CLICK(1,"No File"),
	HOUR(2,"No File"),
	C1(3,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\C1.txt\\part-r-00000"),
	BANNER_POS(4,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	SITE_ID(5,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\site_id.txt\\part-r-00000"),
	SITE_DOMAIN(6,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	SITE_CATEGORY(7,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	APP_ID(8,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	APP_DOMAIN(9,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	APP_CATEGORY(10,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	DEVICE_ID(11,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	DEVICE_IP(12,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	DEVICE_MODEL(13,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	DEVICE_TYPE(14,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	DEVICE_CONN_TYPE(15,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	C14(16,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	C15(17,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	C16(18,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	C17(19,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	C18(20,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	C19(21,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	C20(22,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000"),
	C21(23,"D:\\Kaggle\\AvazuCtrPrediction\\distinctTrainVals\\banner_pos.txt\\part-r-00000");
	
	public int index;
	public String valsFile;
	public static final Field[] fields = new Field[24];
	
	static
	{
		Field values[] = Field.values();
		for(Field field : values)
		{
			fields[field.index] = field;
		}
	}
	
	Field(int index,String valsFile)
	{
		this.index = index;
		this.valsFile = valsFile;
	}

}
