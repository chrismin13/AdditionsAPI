package us.fihgu.toolbox.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks which class this class wrapper wraps.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WarpsClass
{
	/**
	 * NMS class and CraftBukkit class only need to specific path after minecraft version<br>
	 * other class needs the full path.
	 */
	String className();
	ClassType classType() default ClassType.OTHER;
}
