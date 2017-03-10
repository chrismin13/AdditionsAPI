package us.fihgu.toolbox.resourcepack;
import java.net.InetSocketAddress;
import java.nio.file.Paths;

import com.chrismin13.moreminecraft.MoreMinecraft;

import us.fihgu.toolbox.http.FileContext;
import us.fihgu.toolbox.http.HTTPServer;
import us.fihgu.toolbox.http.StaticContextGenerator;
import us.fihgu.toolbox.network.NetworkUtils;

public class ResourcePackServer
{
	public static String localhost;
	public static String host;
	public static int port;
	
	public static String path;
	
	private static HTTPServer server;
	public static void startServer()
	{
		localhost = MoreMinecraft.getInstance().getConfig().getString("http.localhost", NetworkUtils.getLocalIP().getHostAddress());
		host = MoreMinecraft.getInstance().getConfig().getString("http.host", NetworkUtils.getExternalIP());
		port = MoreMinecraft.getInstance().getConfig().getInt("http.port");
		int numReadThread = MoreMinecraft.getInstance().getConfig().getInt("http.numReadThread");
		int numWriteThread = MoreMinecraft.getInstance().getConfig().getInt("http.numWriteThread");
		
		int build = MoreMinecraft.getInstance().getConfig().getInt("resource-pack.build", 1);
		path = "/resourcepack" + build + ".zip";

		InetSocketAddress address = new InetSocketAddress(localhost , port);
		server = new HTTPServer(address);
		server.numReadThread = numReadThread;
		server.numWriteThread = numWriteThread;
		server.putContextGenerator(path, new StaticContextGenerator(new FileContext(Paths.get("./fihgu/toolbox/resource/resource.zip"))));
		server.startServer();
	}
	
	public static void stopServer()
	{
		if(server != null)
		{
			server.stopServer();
		}
	}
}
