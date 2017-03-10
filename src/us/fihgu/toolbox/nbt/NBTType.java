package us.fihgu.toolbox.nbt;


public enum NBTType
{
	END(0),
	BYTE(1),
	SHORT(2),
	INT(3),
	LONG(4),
	FLOAT(5),
	DOUBLE(6),
	BYTE_ARRAY(7),
	STRING(8),
	LIST(9),
	COMPOUND(10),
	INT_ARRAY(11);
	
	private int id;
	
	NBTType(int id)
	{
		this.id = id;
	}
	
	/**
	 * @return the int value that represents this typeId<br>
	 * the int is ready to be used where {@link us.fihgu.toolbox.nbt.NBTCompoundWrapper NBTCompoundWrapper} requires an typeId<br>
	 */
	public int getId()
	{
		return this.id;
	}
	
	public static NBTType getNBTType(byte b)
	{
		switch(b)
		{
		case 0:
			return NBTType.END;
		case 1:
			return NBTType.BYTE;
		case 2:
			return NBTType.SHORT;
		case 3:
			return NBTType.INT;
		case 4:
			return NBTType.LONG;
		case 5:
			return NBTType.FLOAT;
		case 6:
			return NBTType.DOUBLE;
		case 7:
			return NBTType.BYTE_ARRAY;
		case 8:
			return NBTType.STRING;
		case 9:
			return NBTType.LIST;
		case 10:
			return NBTType.COMPOUND;
		case 11:
			return NBTType.INT_ARRAY;
		default:
			return null;
		}
	}
}
