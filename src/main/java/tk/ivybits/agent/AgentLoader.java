package tk.ivybits.agent;

import static tk.ivybits.agent.Tools.getBytesFromStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

/**
 * A utility class for loading Java agents.
 *
 * @author Tudor
 */
public class AgentLoader {

    /**
     * Loads an agent into a JVM.
     *
     * @param agent     The main agent class.
     * @param resources Array of classes to be included with agent.
     * @param pid       The ID of the target JVM.
     * @throws IOException
     * @throws AttachNotSupportedException
     * @throws AgentLoadException
     * @throws AgentInitializationException
     */
    public static void attachAgentToJVM(String pid, Class<?> agent, Class<?>... resources)
            throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {

        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent(generateAgentJar(agent, resources).getAbsolutePath());
        vm.detach();
    }

    /**
     * Generates a temporary agent file to be loaded.
     *
     * @param agent     The main agent class.
     * @param resources Array of classes to be included with agent.
     * @return Returns a temporary jar file with the specified classes included.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static File generateAgentJar(Class<?> agent, Class<?>... resources) throws IOException {
        File jarFile = File.createTempFile("agent", ".jar");
        jarFile.deleteOnExit();

        Manifest manifest = new Manifest();
        Attributes mainAttributes = manifest.getMainAttributes();
        // Create manifest stating that agent is allowed to transform classes
        mainAttributes.put(Name.MANIFEST_VERSION, "1.0");
        mainAttributes.put(new Name("Agent-Class"), agent.getName());
        mainAttributes.put(new Name("Can-Retransform-Classes"), "true");
        mainAttributes.put(new Name("Can-Redefine-Classes"), "true");

        JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile), manifest);

        jos.putNextEntry(new JarEntry(agent.getName().replace('.', '/') + ".class"));

        jos.write(getBytesFromStream(agent.getClassLoader().getResourceAsStream(unqualify(agent))));
        jos.closeEntry();

        for (Class<?> clazz : resources) {
            String name = unqualify(clazz);
            jos.putNextEntry(new JarEntry(name));
            jos.write(getBytesFromStream(clazz.getClassLoader().getResourceAsStream(name)));
            jos.closeEntry();
        }

        jos.close();
        return jarFile;
    }

    private static String unqualify(Class<?> clazz) {
        return clazz.getName().replace('.', '/') + ".class";
    }
}
