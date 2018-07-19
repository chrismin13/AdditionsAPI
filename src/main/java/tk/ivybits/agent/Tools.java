package tk.ivybits.agent;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;

import com.chrismin13.additionsapi.AdditionsAPI;

/**
 * Various IO tools.
 *
 * @author Tudor
 */
public class Tools {
    private static final String REV = "1";
    private static final String NATIVE_DIR = "natives/";
    private static final String WIN_DIR = "windows/";
    private static final String NIX_DIR = "linux/";
    private static final String MAC_DIR = "mac/";
    private static final String SOLARIS_DIR = "solaris/";
    private static final String CACHE_DIR = System.getProperty("java.io.tmpdir") + File.separatorChar + "agentcache.0.0_" + REV;
    public static String nativeLibTmpFile;

    /**
     * Gets the current JVM PID.
     *
     * @return Returns the PID.
     */
    public static String getCurrentPID() {
        String jvm = ManagementFactory.getRuntimeMXBean().getName();
        return jvm.substring(0, jvm.indexOf('@'));
    }

    public static byte[] getBytesFromStream(InputStream stream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[65536];
        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();

    }

    /**
     * Gets bytes from class
     *
     * @param clazz The class.
     * @return Returns a byte[] representation of given class.
     * @throws IOException
     */
    public static byte[] getBytesFromClass(Class<?> clazz) throws IOException {
        return getBytesFromStream(clazz.getClassLoader().getResourceAsStream(clazz.getName().replace('.', '/') + ".class"));
    }

    /**
     * Gets bytes from resource
     *
     * @param resource The resource string.
     * @return Returns a byte[] representation of given resource.
     * @throws IOException
     */
    public static byte[] getBytesFromResource(ClassLoader clazzLoader, String resource) throws IOException {
        return getBytesFromStream(clazzLoader.getResourceAsStream(resource));
    }

    /**
     * Adds a a path to the current java.library.path.
     *
     * @param path The path.
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static void addToLibPath(String path) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        if (System.getProperty("java.library.path") != null) {
            // If java.library.path is not empty, we will prepend our path
            // Note that path.separator is ; on Windows and : on *nix,
            // so we can't hard code it.
            System.setProperty("java.library.path", path + System.getProperty("path.separator") + System.getProperty("java.library.path"));
        } else {
            System.setProperty("java.library.path", path);
        }

        // Important: java.library.path is cached
        // We will be using reflection to clear the cache
        Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
        fieldSysPath.setAccessible(true);
        fieldSysPath.set(null, null);

    }

    /**
     * Extracts a resource to specified path.
     *
     * @param loader
     * @param resourceName
     * @param targetName
     * @param targetDir
     * @throws IOException
     */
    public static void extractResourceToDirectory(ClassLoader loader, String resourceName, String targetName, String targetDir)
            throws IOException {
        InputStream source = loader.getResourceAsStream(resourceName);
        File tmpdir = new File(targetDir);
        File target = new File(tmpdir, targetName);
        target.createNewFile();

        FileOutputStream stream = new FileOutputStream(target);
        byte[] buf = new byte[65536];
        int read;
        while ((read = source.read(buf)) != -1) {
            stream.write(buf, 0, read);
        }
        stream.close();
        source.close();
    }

    /**
     * Attempts to load an attach library.
     */
    public static void loadAgentLibrary() {
        switch (Platform.getPlatform()) {
            case WINDOWS:
                unpack(WIN_DIR + "attach.dll");
                break;
            case LINUX:
                unpack(NIX_DIR + "libattach.so");
                break;
            case MAC:
                unpack(MAC_DIR + "libattach.dylib");
                break;
            case SOLARIS:
                unpack(SOLARIS_DIR + "libattach.so");
                break;
            default:
                throw new UnsupportedOperationException("unsupported platform");
        }
    }

    private static void unpack(String path) {
        try {
            System.out.println(NATIVE_DIR + ((Platform.is64Bit() || Platform.getPlatform() == Platform.MAC) ? "64/" : "32/") + path);
            InputStream in = AdditionsAPI.getInstance().getResource(NATIVE_DIR + ((Platform.is64Bit() || Platform.getPlatform() == Platform.MAC) ? "64/" : "32/") + path);
            
            File pathDir = new File(CACHE_DIR);
            pathDir.mkdirs();
            File libfile = new File(pathDir, path.substring(path.lastIndexOf("/"), path.length()));
            Tools.nativeLibTmpFile = libfile.getAbsolutePath();

            if (!libfile.exists()) {
                libfile.deleteOnExit();
                OutputStream out = new BufferedOutputStream(new FileOutputStream(libfile));

                int len;
                byte[] buffer = new byte[8192];
                while ((len = in.read(buffer)) > -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.close();
                in.close();
            }
        } catch (IOException x) {
            throw new RuntimeException("could not unpack binaries", x);
        }
    }

    public enum Platform {

        LINUX, WINDOWS, MAC, SOLARIS;

        public static Platform getPlatform() {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.indexOf("win") >= 0) {
                return WINDOWS;
            }
            if ((os.indexOf("nix") >= 0) || (os.indexOf("nux") >= 0) || (os.indexOf("aix") > 0)) {
                return LINUX;
            }
            if (os.indexOf("mac") >= 0) {
                return MAC;
            }
            if (os.indexOf("sunos") >= 0)
                return SOLARIS;
            return null;
        }

        public static boolean is64Bit() {
            String osArch = System.getProperty("os.arch");
            return "amd64".equals(osArch) || "x86_64".equals(osArch);
        }
    }
}
