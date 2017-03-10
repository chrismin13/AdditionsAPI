package us.fihgu.toolbox.nbt;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.inventory.ItemStack;

import us.fihgu.toolbox.reflection.ReflectionUtils;

public class NBTUtils
{
	/**
	 * gets the NBT compound of given itemstack.<br>
	 * this compound contains all meta info of an itemstack<br>
	 * but it does not contain item type or damage, amount.<br>
	 */
	public static NBTCompoundWrapper getNBTTag(ItemStack item)
	{
		Class<?> craftItemStackClass = ReflectionUtils.getCraftBukkitClass("inventory.CraftItemStack");
		Class<?> nmsItemStackClass = ReflectionUtils.getNMSClass("ItemStack");
		try
		{
			Object nmsItemStack = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
			Object nmsNBT = nmsItemStackClass.getMethod("getTag").invoke(nmsItemStack);
			
			if(nmsNBT == null)
			{
				return null;
			}
			
			NBTCompoundWrapper nbt = new NBTCompoundWrapper(nmsNBT);
			
			if(nbt != null)
			{
				return nbt;
			}
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void setNBTTag(ItemStack item, NBTCompoundWrapper nbt)
	{
		Class<?> craftItemStackClass = ReflectionUtils.getCraftBukkitClass("inventory.CraftItemStack");
		Class<?> nmsItemStackClass = ReflectionUtils.getNMSClass("ItemStack");
		try
		{
			Object nmsItemStack = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
			nmsItemStackClass.getMethod("setTag", nbt.getInstance().getClass()).invoke(nmsItemStack, nbt.getInstance());
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
	}
}
