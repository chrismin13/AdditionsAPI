package us.fihgu.toolbox.nbt;

import us.fihgu.toolbox.reflection.ClassType;
import us.fihgu.toolbox.reflection.WarpsClass;

@WarpsClass(className = "NBTTagCompound", classType = ClassType.NMS)
public class NBTCompoundWrapper extends NBTBaseWrapper
{	
	public NBTCompoundWrapper()
	{
		try
		{
			this.instance = this.getWrappedClass().getConstructor().newInstance();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public NBTCompoundWrapper(Object instance)
	{
		super(instance);
	}
	
	
	public void set(String key, NBTBaseWrapper nbtBase)
	{
		this.invoke("set", void.class, new Class<?>[]{String.class, getWrappedClass(NBTBaseWrapper.class)}, new Object[]{key, nbtBase.getInstance()});
	}
	public void setByte(String key, byte b)
	{
		this.invoke("setByte", void.class, new Class<?>[]{String.class, byte.class}, new Object[]{key, b});
	}
	public void setShort(String key, short s)
	{
		this.invoke("setShort", void.class, new Class<?>[]{String.class, short.class}, new Object[]{key, s});
	}
	public void setInt(String key, int i)
	{
		this.invoke("setInt", void.class, new Class<?>[]{String.class, int.class}, new Object[]{key, i});
	}
	public void setLong(String key, long l)
	{
		this.invoke("setLong", void.class, new Class<?>[]{String.class, long.class}, new Object[]{key, l});
	}
	public void setFloat(String key, float f)
	{
		this.invoke("setFloat", void.class, new Class<?>[]{String.class, float.class}, new Object[]{key, f});
	}
	public void setDouble(String key, double d)
	{
		this.invoke("setDouble", void.class, new Class<?>[]{String.class, double.class}, new Object[]{key, d});
	}
	public void setString(String key, String text)
	{
		this.invoke("setString", void.class, new Class<?>[]{String.class, String.class}, new Object[]{key, text});
	}
	public void setByteArray(String key, byte[] bytes)
	{
		this.invoke("setByteArray", void.class, new Class<?>[]{String.class, byte[].class}, new Object[]{key, bytes});
	}
	public void setIntArray(String key, int[] ints)
	{
		this.invoke("setIntArray", void.class, new Class<?>[]{String.class, int[].class}, new Object[]{key, ints});
	}
	public void setBoolean(String key, boolean bool)
	{
		this.invoke("setBoolean", void.class, new Class<?>[]{String.class, boolean.class}, new Object[]{key, bool});
	}
	
	
	
	public NBTBaseWrapper get(String key)
	{
		Object result = this.invoke("get", getWrappedClass(NBTBaseWrapper.class), new Class<?>[]{String.class}, new Object[]{key});
		if(result != null)
		{
			return new NBTBaseWrapper(result);
		}
		else
		{
			return null;
		}
	}
	public NBTCompoundWrapper getCompound(String key)
	{
		Object result = this.invoke("getCompound", getWrappedClass(NBTCompoundWrapper.class), new Class<?>[]{String.class}, new Object[]{key});
		if(result != null)
		{
			return new NBTCompoundWrapper(result);
		}
		else
		{
			return null;
		}
	}
	public NBTListWrapper getList(String key, int typeId)
	{
		Object result = this.invoke("getList", getWrappedClass(NBTListWrapper.class), new Class<?>[]{String.class, int.class}, new Object[]{key, typeId});
		if(result != null)
		{
			return new NBTListWrapper(result);
		}
		else
		{
			return null;
		}
	}
	public Byte getByte(String key)
	{
		return this.invoke("getByte", Byte.class, new Class<?>[]{String.class}, new Object[]{key});
	}
	public Short getShort(String key)
	{
		return this.invoke("getShort", Short.class, new Class<?>[]{String.class}, new Object[]{key});
	}
	public Integer getInt(String key)
	{
		return this.invoke("getInt", Integer.class, new Class<?>[]{String.class}, new Object[]{key});
	}
	public Long getLong(String key)
	{
		return this.invoke("getLong", Long.class, new Class<?>[]{String.class}, new Object[]{key});
	}
	public Float getFloat(String key)
	{
		return this.invoke("getFloat", Float.class, new Class<?>[]{String.class}, new Object[]{key});
	}
	public Double getDouble(String key)
	{
		return this.invoke("getDouble", Double.class, new Class<?>[]{String.class}, new Object[]{key});
	}
	public String getString(String key)
	{
		return this.invoke("getString", String.class, new Class<?>[]{String.class}, new Object[]{key});
	}
	public byte[] getByteArray(String key)
	{
		return this.invoke("getByteArray", byte[].class, new Class<?>[]{String.class}, new Object[]{key});
	}
	public int[] getIntArray(String key)
	{
		return this.invoke("getIntArray", int[].class, new Class<?>[]{String.class}, new Object[]{key});
	}
	public boolean getBoolean(String key)
	{
		return this.invoke("getBoolean", boolean.class, new Class<?>[]{String.class}, new Object[]{key});
	}
	public void remove(String key)
	{
		this.invoke("remove", void.class, new Class<?>[]{String.class}, new Object[]{key});
	}
	
	public boolean hasKey(String key)
	{
		return this.invoke("hasKey", boolean.class, new Class<?>[]{String.class}, new Object[]{key});
	}
	
	public boolean hasKeyOfType(String key, NBTType typeId)
	{
		return this.invoke("hasKeyOfType", boolean.class, new Class<?>[]{String.class, int.class}, new Object[]{key, typeId.getId()});
	}
	
	public boolean isEmpty()
	{
		return this.invoke("isEmpty", boolean.class);
	}
	
	public byte getTypeId()
	{
		return this.invoke("getTypeId", byte.class);
	}
	
	public String toString()
	{		
		return this.invoke("toString", String.class);
	}
}
