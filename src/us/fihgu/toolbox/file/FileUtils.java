package us.fihgu.toolbox.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.bukkit.plugin.java.JavaPlugin;

public class FileUtils {

	/**
	 * Create the given file if it doesn't exist, will also create directories
	 * if they do not exist.<br>
	 * If the file already exist, nothing will happen.
	 */
	public static void createFileAndPath(File file) throws IOException {

		if (!file.exists()) {
			String filePath = file.getPath();
			int index = filePath.lastIndexOf(File.separator);

			if (index >= 0) {
				String folderPath = filePath.substring(0, index);
				File folder = new File(folderPath);
				if (!folder.exists()) {
					folder.mkdirs();
				}
			}

			file.createNewFile();
		}
	}

	public static void copyURLtoFile(URL url, File file) throws IOException {
		org.apache.commons.io.FileUtils.copyURLToFile(url, file);
	}

	public static void copyFolder(File from, File to) throws IOException {
		org.apache.commons.io.FileUtils.copyDirectory(from, to);
	}

	public static void copyFile(File from, File to) throws IOException {
		org.apache.commons.io.FileUtils.copyFile(from, to);
	}

	public static void deleteFolder(File folder) throws IOException {
		org.apache.commons.io.FileUtils.deleteDirectory(folder);
	}

	public static void copyStreams(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[4096];
		int length = 0;

		try {
			while ((length 
					= in.read
					(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
		} finally {
			out.flush();
			out.close();
			in.close();
		}
	}

	public static void unzip(File zipFile, File directroy) throws IOException {
		ZipInputStream in = new ZipInputStream(new FileInputStream(zipFile));
		ZipEntry entry;
		while ((entry = in.getNextEntry()) != null) {
			String name = entry.getName();
			File output = new File(directroy, name);

			if (!entry.isDirectory()) {
				createFileAndPath(output);
				FileOutputStream out = new FileOutputStream(output);
				byte[] buffer = new byte[4096];
				int byteRead;
				while ((byteRead = in.read(buffer)) > 0) {
					out.write(buffer, 0, byteRead);
				}
				out.flush();
				out.close();
			} else {
				output.mkdirs();
			}
		}
		in.close();
	}

	public static void zip(File source, File output) throws IOException {
		createFileAndPath(output);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(output));
		addToZip(source, null, out);
		out.close();
	}

	/**
	 * copy a resource file inside the plugin's jar file
	 */
	public static void copyResource(JavaPlugin plugin, String resource, File fileOut) throws IOException {
		InputStream in = plugin.getResource(resource);
		if (!fileOut.exists()) {
			createFileAndPath(fileOut);
		}

		FileOutputStream out = new FileOutputStream(fileOut);
		copyStreams(in, out);
	}

	private static void addToZip(File file, String path, ZipOutputStream out) throws IOException {
		if (file != null && file.isDirectory()) {
			for (String name : file.list()) {
				File tempFile = new File(file, name);
				String fullPath;
				if (path == null) {
					fullPath = name;
				} else {
					fullPath = path + "/" + name;
				}
				if (tempFile.isDirectory()) {
					ZipEntry entry = new ZipEntry(fullPath + "/");
					out.putNextEntry(entry);
				}
				addToZip(tempFile, fullPath, out);
			}
		} else {
			FileInputStream in = new FileInputStream(file);
			ZipEntry entry = new ZipEntry(path);
			out.putNextEntry(entry);
			byte[] buffer = new byte[4096];
			int byteRead;
			while ((byteRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, byteRead);
			}
			in.close();
			out.closeEntry();
		}
	}

	public static void serialize(Serializable object, File file) {
		if (!file.exists()) {
			try {
				createFileAndPath(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object deserialize(File file) {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
			Object obj = in.readObject();
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @param plugin
	 * @return the jar file of the given plugin
	 */
	public static String getPluginJarPath(JavaPlugin plugin) {
		String path = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath())
				.getAbsolutePath();
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return path;
	}
}
