package us.fihgu.toolbox.nbt;

import us.fihgu.toolbox.reflection.ClassType;
import us.fihgu.toolbox.reflection.WarpsClass;

@WarpsClass(className = "NBTTagList", classType = ClassType.NMS)
public class NBTListWrapper extends NBTBaseWrapper
{	
	public NBTListWrapper()
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
	
	public NBTListWrapper(Object instance)
	{
		super(instance);
	}
	
	public void add(NBTBaseWrapper base)
	{
		this.invoke("add", void.class, new Class<?>[]{getWrappedClass(NBTBaseWrapper.class)}, new Object[]{base.getInstance()});
	}
	
	public NBTBaseWrapper remove(int index)
	{		
		Object result = this.invoke("remove", getWrappedClass(NBTBaseWrapper.class), new Class<?>[]{int.class}, new Object[]{index});
		
		if(result != null)
		{
			return new NBTBaseWrapper(result);
		}
		else
		{
			return null;
		}
	}
	
	public boolean isEmpty()
	{
		return this.invoke("isEmpty", boolean.class);
	}
	
	public NBTCompoundWrapper get(int index)
	{
		Object result = this.invoke("get", getWrappedClass(NBTCompoundWrapper.class), new Class<?>[]{int.class}, new Object[]{index});
		
		if(result != null)
		{
			return new NBTCompoundWrapper(result);
		}
		else
		{
			return null;
		}
	}
	
	public String getString(int index)
	{
		return this.invoke("getString", String.class, new Class<?>[]{int.class}, new Object[]{index});
	}
	
	public int size()
	{
		return this.invoke("size", int.class);
	}
	
	public String toString()
	{		
		return this.invoke("toString", String.class);
	}
}
