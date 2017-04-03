package us.fihgu.toolbox.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;

import com.chrismin13.moreminecraft.utils.Debug;

import us.fihgu.toolbox.web.SelectionHandler;
import us.fihgu.toolbox.web.SelectorThreadPool;
import us.fihgu.toolbox.web.TimerThread;
import us.fihgu.toolbox.web.WebServer;

/**
 * This HTTP Server runs in its own thread.<br>
 * It uses non-block nio channels.<br>
 * @author fihgu
 *
 */
public class HTTPServer extends WebServer
{
	protected SelectorThreadPool readPool;
	protected SelectorThreadPool writePool;
	
	protected HTTPAcceptHandler acceptHandler = new HTTPAcceptHandler(this);
	
	protected CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
	protected CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
	
	protected TimerThread timer = new TimerThread(this);
	
	public boolean debug = false;
	public boolean info = true;
	
	public int readBufferSize = 128;
	public int lineBuilderSize = 256;
	
	public int numReadThread = 4;
	public int numWriteThread = 4;
	
	public long timeOut = 120000;
	
	protected HashMap<String, IContextGenerator> contextGenerators = new HashMap<String, IContextGenerator>();

	public HTTPServer(InetSocketAddress address)
	{
		super(address);
	}
	
	public HTTPContext getContext(HTTPRequest request) throws MalformedURLException
	{
		String host = "unknown";
		
		if(request.version.equals("HTTP/1.1"))
		{
			String temp = request.headers.get("host");
			if(temp != null)
			{
				host = temp;
			}
		}
		
		URL path = new URL("http://" + host + request.path);
		
        IContextGenerator generator = this.contextGenerators.get(path.getPath());
        if(generator != null)
        {
        	return generator.generateContext(request);
        }
		
		return null;
	}
	
	/**
	 * @param path should contain the host name(for example: www.example.com/path/to/file.html)
	 * @param generator
	 */
	public void putContextGenerator(String path, IContextGenerator generator)
	{
		this.contextGenerators.put(path, generator);
	}
	
	public void removeContextGenerator(String path)
	{
		this.contextGenerators.remove(path);
	}
	
	@Override
	protected void init() throws IOException
	{
		readPool = new SelectorThreadPool(new HTTPReadHandler(this), this.numReadThread);
		readPool.startAll();
		writePool = new SelectorThreadPool(new HTTPWriteHandler(this), this.numWriteThread);
		writePool.startAll();
		timer.start();
		super.init();
	}
	
	

	@Override
	public void startServer()
	{
		super.startServer();
		if(this.info)
		{
			Debug.sayTrue("HTTP Server binded to: " + this.address);
		}
	}

	@Override
	public void stopServer()
	{
		super.stopServer();
		timer.stopThread();
		readPool.closeAll();
		writePool.closeAll();
	}
	
	

	@Override
	public void onTimeOut(SelectionKey selectionKey)
	{
		if(this.info)
		{
			try
			{
				Debug.sayTrue("HTTP connection timeout: " + ((SocketChannel)selectionKey.channel()).getRemoteAddress());
			}
			catch (IOException e)
			{
				//should not happen
				e.printStackTrace();
			}
		}
		super.onTimeOut(selectionKey);
	}

	@Override
	public SelectionHandler getAcceptHandler()
	{
		return acceptHandler;
	}
}
