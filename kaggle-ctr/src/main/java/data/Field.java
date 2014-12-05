package data;

public enum Field {
	
	ID(0),CLICK(1),HOUR(2),C1(3),BANNER_POS(4),SITE_ID(5),SITE_DOMAIN(6),SITE_CATEGORY(7),APP_ID(8),APP_DOMAIN(9),
	APP_CATEGORY(10),DEVICE_ID(11),DEVICE_IP(12),DEVICE_MODEL(13),DEVICE_TYPE(14),DEVICE_CONN_TYPE(15),C14(16),C15(17),
	C16(18),C17(19),C18(20),C19(21),C20(22),C21(23);
	
	public int index;
	public static final Field[] fields = new Field[24];
	
	static
	{
		Field values[] = Field.values();
		for(Field field : values)
		{
			fields[field.index] = field;
		}
	}
	
	Field(int index)
	{
		this.index = index;
	}

}
