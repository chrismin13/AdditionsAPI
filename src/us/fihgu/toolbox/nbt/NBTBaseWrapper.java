package us.fihgu.toolbox.nbt;

import us.fihgu.toolbox.reflection.ClassType;
import us.fihgu.toolbox.reflection.WarpsClass;
import us.fihgu.toolbox.reflection.WrapperBase;

@WarpsClass(className = "NBTBase", classType = ClassType.NMS)
public class NBTBaseWrapper extends WrapperBase
{
	public NBTBaseWrapper(Object instance)
	{
		super(instance);
	}
	
	protected NBTBaseWrapper()
	{
		
	}
	
	@Override
	public NBTBaseWrapper clone()
	{
		Object cloneInstance = this.invoke("clone", this.getWrappedClass());
		return new NBTBaseWrapper(cloneInstance);
	}
}
