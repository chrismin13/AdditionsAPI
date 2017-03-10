package us.fihgu.toolbox.web;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * A non block web server that handles the input and output of data though the network. <br>
 */
public abstract class WebServer
{
	protected boolean isRunning = false;
	protected InetSocketAddress address;
	protected ServerSocketChannel serverChannel;
	protected SelectorThread acceptSelectorThread;
	protected CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
	
	public WebServer(InetSocketAddress address)
	{
		this.address = address;
	}
	
	protected void init() throws IOException
	{
		serverChannel = ServerSocketChannel.open();
		serverChannel.bind(address, address.getPort());
		serverChannel.configureBlocking(false);
		
		acceptSelectorThread = new SelectorThread(this.getAcceptHandler());
		acceptSelectorThread.register(this.serverChannel);
		acceptSelectorThread.start();
	}
	
	/**
	 * @return a AcceptHandler that will handle {@link SelectionKey#OP_ACCEPT}.
	 */
	public abstract SelectionHandler getAcceptHandler();
	
	public void startServer()
	{
		if(!this.isRunning)
		{
			this.isRunning = true;
			try
			{
				this.init();
			} 
			catch (IOException e)
			{
				System.err.println("could not start web server on: " + this.address.toString());
				this.stopServer();
				e.printStackTrace();
			}
		}
		else
		{
			new Exception("this server is already running on: " + this.address.toString()).printStackTrace();
		}
	}
	
	public void stopServer()
	{
		if(this.acceptSelectorThread != null)
		{
			try
			{
				synchronized (acceptSelectorThread)
				{
					if(!acceptSelectorThread.isStopped())
					{
						acceptSelectorThread.closeSelector();
						acceptSelectorThread.wait();
					}
				}
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			this.acceptSelectorThread = null;
		}
		
		if(this.serverChannel != null)
		{
			try
			{
				this.serverChannel.close();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			this.serverChannel = null;
		}
		
		this.isRunning = false;
	}
	
	public void onTimeOut(SelectionKey selectionKey)
	{
		selectionKey.cancel();
		try
		{
			selectionKey.channel().close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isRunning()
	{
		return this.isRunning;
	}
}
