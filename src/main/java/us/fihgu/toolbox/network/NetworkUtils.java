package us.fihgu.toolbox.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.chrismin13.additionsapi.utils.Debug;

public class NetworkUtils {

	/**
	 * @return the non loop back LAN IP of this computer.
	 */
	public static InetAddress getLocalIP() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (!networkInterface.isLoopback()) {
					Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						InetAddress address = addresses.nextElement();
						if (address.isSiteLocalAddress()) {
							return address;
						}
					}
				}
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		}

		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return InetAddress.getLoopbackAddress();
	}

	/**
	 * 
	 * @return
	 */
	public static String getExternalIP() {
		String ip = null;
		try {
			URL whatismyip = new URL("http://agentgatech.appspot.com");
			URLConnection connection = whatismyip.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			ip = in.readLine();
		} catch (IOException e) {
			try {
				// appspot.com is blocked in China as it is from Google. The following is a good alternative.
				URL whatismyip2 = new URL("http://checkip.amazonaws.com/");
				URLConnection connection2 = whatismyip2.openConnection();
				BufferedReader in2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
				ip = in2.readLine();
			} catch (IOException e2) {
				Debug.sayError(
						"A connection to agentgatech.appspot.com or checkip.amazonaws.com could not be achieved! Please check your internet connection and if the website is down!");
				Debug.sayError("Localhost will be used instead.");
			}

		}

		return ip;
	}
}
