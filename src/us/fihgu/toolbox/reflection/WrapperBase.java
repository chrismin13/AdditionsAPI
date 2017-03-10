package us.fihgu.toolbox.reflection;

import java.lang.reflect.Method;

/**
 * This class wraps over another class though reflection<br>
 * You may invoke methods of the wrapped instance though {@link #invoke(String, Class, Class[], Object[]) } method.<br>
 * <br>
 * Any child class of this class should have a @WrapsClass annotation.
 */
@WarpsClass(className = "java.lang.Object", classType = ClassType.OTHER)
public class WrapperBase
{
	/**
	 * the wrapped instance.
	 */
	protected Object instance;

	protected Class<?> getWrappedClass()
	{
		return getWrappedClass(this.getClass());
	}
	
	protected static Class<?> getWrappedClass(Class<? extends WrapperBase> theClass)
	{
		WarpsClass annotation = theClass.getAnnotationsByType(WarpsClass.class)[0];
		String name = annotation.className();
		ClassType type = annotation.classType();

		switch (type)
		{
		case CRAFTBUKKIT:
			return ReflectionUtils.getCraftBukkitClass(name);
		case NMS:
			return ReflectionUtils.getNMSClass(name);
		case OTHER:
		default:
			return ReflectionUtils.getClass(name);
		}
	}

	/**
	 * accepts an instance of the matching class
	 */
	public WrapperBase(Object instance)
	{
		if (instance == null)
		{
			throw new NullPointerException();
		}

		if (this.getWrappedClass().isInstance(instance))
		{
			this.instance = instance;
		} else
		{
			throw new IllegalArgumentException();
		}
	}

	public WrapperBase()
	{

	}
	
	/**
	 * invoke a method from the wrapped class.<br>
	 * @param methodName the name of this method
	 * @param returnType if null is used, its considered same as void.class.
	 * @param argsClass argument Class types from the class you are trying to invoke.
	 * @param args arguments of the method, use {@link #invoke(String, Class)} if there is no argument.
	 */
	public <T> T invoke(String methodName, Class<T> returnType, Class<?>[] argsClass, Object[] args)
	{		
		try
		{
			Method method = this.getWrappedClass().getMethod(methodName, argsClass);
			Object result = method.invoke(this.getInstance(), args);
			
			if(returnType != null && returnType != void.class)
			{
				return returnType.cast(result);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @see #invoke(String, Class, Class[], Object[])
	 */
	public <T> T invoke(String methodName, Class<T> returnType)
	{
		return this.invoke(methodName, returnType, new Class<?>[]{}, new Object[]{});
	}
	
	/**
	 * 
	 * @return the wrapped instance
	 */
	public Object getInstance()
	{
		return this.instance;
	}

	/**
	 * if the class being wrapped by this class is an instance of the class being wrapped by given wrapperClass<br>
	 */
	public boolean isInstanceof(Class<? extends WrapperBase> wrapperClass)
	{
		return getWrappedClass(wrapperClass).isInstance(this.getInstance());
	}
}
