package us.fihgu.toolbox.resourcepack;

import java.net.InetSocketAddress;
import java.nio.file.Paths;

import com.chrismin13.additionsapi.AdditionsAPI;

import us.fihgu.toolbox.http.FileContext;
import us.fihgu.toolbox.http.HTTPServer;
import us.fihgu.toolbox.http.StaticContextGenerator;
import us.fihgu.toolbox.network.NetworkUtils;

public class ResourcePackServer {
	public static String localhost;
	public static String host;
	public static int port;

	public static String path;

	private static HTTPServer server;

	public static void startServer() {
		localhost = AdditionsAPI.getInstance().getConfig().getString("http.localhost",
				NetworkUtils.getLocalIP().getHostAddress());
		host = AdditionsAPI.getInstance().getConfig().getString("http.host", NetworkUtils.getExternalIP());
		port = AdditionsAPI.getInstance().getConfig().getInt("http.port");
		int numReadThread = AdditionsAPI.getInstance().getConfig().getInt("http.numReadThread");
		int numWriteThread = AdditionsAPI.getInstance().getConfig().getInt("http.numWriteThread");

		int build = AdditionsAPI.getInstance().getConfig().getInt("resource-pack.build", 1);
		path = "/resourcepack" + build + ".zip";

		InetSocketAddress address = new InetSocketAddress(localhost, port);
		server = new HTTPServer(address);
		server.numReadThread = numReadThread;
		server.numWriteThread = numWriteThread;
		server.putContextGenerator(path, new StaticContextGenerator(new FileContext(
				Paths.get(AdditionsAPI.getInstance().getDataFolder() + "/resource-pack/resource.zip"))));
		server.startServer();
	}

	public static void stopServer() {
		if (server != null) {
			server.stopServer();
		}
	}
}
