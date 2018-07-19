package us.fihgu.toolbox.json;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import us.fihgu.toolbox.file.FileUtils;

public class JsonUtils
{
	static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * Read a json object from the given file, and try to cast the read object to the given class.<br>
	 * @return the read object, or null if the file doesn't exist.
	 * @throws JsonSyntaxException when there is a syntax error in this json file
	 */
	public static <T> T fromFile(File file, Class<T> type)
	{
		if(file.exists())
		{
			StringBuilder str = new StringBuilder();
			try(Scanner scan = new Scanner(new FileInputStream(file)))
			{
				while(scan.hasNextLine())
				{
					str.append(scan.nextLine());
				}
				
				return gson.fromJson(str.toString(), type);
			}
			catch (FileNotFoundException e)
			{
				//should never happen
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Write the given object to the given file. <br>
	 * <b>Note: If file already contain data, the old data will be discarded.</b>
	 */
	public static void toFile(File file, Object obj)
	{
		if(!file.exists())
		{
			try
			{
				FileUtils.createFileAndPath(file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		String str = toJson(obj);
		try(PrintWriter out = new PrintWriter(new FileOutputStream(file, false)))
		{
			out.print(str);
			out.flush();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return A json string representation of this object.
	 */
	public static String toJson(Object obj)
	{
		return gson.toJson(obj);
	}

	/**
	 * See {@link Gson#fromJson(String, Class)}
	 */
	public static <T> T fromJson(String json, Class<T> type)
	{
		return gson.fromJson(json, type);
	}
}
