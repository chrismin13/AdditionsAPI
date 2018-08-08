package us.fihgu.toolbox.reflection;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;

public class ReflectionUtils {
	final public static String NMSPath = "net.minecraft.server.";
	final public static String craftbukkitPath = "org.bukkit.craftbukkit.";

	/**
	 * gets the current version of minecraft.
	 */
	public static String getMinecraftVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}

	public static Class<?> getClass(String className) {
		try {
			Class<?> result = Class.forName(className);
			return result;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Class<?> getNMSClass(String className) {
		return getClass(NMSPath + getMinecraftVersion() + "." + className);
	}

	public static Class<?> getCraftBukkitClass(String className) {
		return getClass(craftbukkitPath + getMinecraftVersion() + "." + className);
	}

	public static Field findUnderlyingField(Class<?> clazz, String fieldName) {
		Class<?> current = clazz;
		do {
			try {
				return current.getDeclaredField(fieldName);
			} catch (Exception e) {
			}
		} while ((current = current.getSuperclass()) != null);
		return null;
	}
}
