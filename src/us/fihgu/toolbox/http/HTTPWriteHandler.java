package us.fihgu.toolbox.http;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;

import us.fihgu.toolbox.web.SelectionHandler;

public class HTTPWriteHandler implements SelectionHandler
{
	protected HTTPServer server;
	public HTTPWriteHandler(HTTPServer server)
	{
		this.server = server;
	}
	
	@Override
	public void handleSelection(SelectionKey selectionKey)
	{
		if(selectionKey.isWritable())
		{
			HTTPResponse response = (HTTPResponse) selectionKey.attachment();
			SocketChannel channel = (SocketChannel) selectionKey.channel();
			
			if(response.buffer == null)
			{
				this.buildBuffer(response);
			}
			
			try
			{
				if(response.buffer.hasRemaining())
				{
					this.writeBuffer(channel, response);
				}
				else
				{
					if(response.context != null && response.contextPosition < response.context.getSize())
					{
						this.writeContext(channel, response);
					}
					else
					{
						if(server.debug)
						{
							System.out.println("");
							System.out.println("Response sent to: " + channel.getRemoteAddress());
							System.out.println(response.toString());
							System.out.println("");
						}
						this.closeChannel(selectionKey);
					}
				}
			}
			catch(IOException e)
			{
				this.closeChannel(selectionKey);
				if(server.debug)
				{
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private void writeContext(SocketChannel channel, HTTPResponse response) throws IOException
	{
		if(response.context instanceof IReleasable)
		{
			if(response.accessPoint == null)
			{
				IReleasable releasable = (IReleasable)response.context;
				response.accessPoint = releasable.aquireAccess();
			}
		}
		
		long byteRead = response.context.read(channel, response.contextPosition);
		if(byteRead == -1)
		{
			throw new IOException("End of Stream");
		}
		else
		{
			response.contextPosition += byteRead;
		}
	}
	
	private void writeBuffer(SocketChannel channel, HTTPResponse response) throws IOException
	{
		int byteRead = channel.write(response.buffer);
		
		if(byteRead == -1)
		{
			throw new IOException("End of Stream");
		}
	}
	
	private void buildBuffer(HTTPResponse response)
	{
		String headerString = response.getHeaderString();
		CharBuffer charBuffer = CharBuffer.wrap(headerString.toCharArray());
		try
		{
			response.buffer = server.encoder.encode(charBuffer);
		}
		catch (CharacterCodingException e)
		{
			//should not happen
			e.printStackTrace();
		}
	}
	
	/**
	 * unregister the SelectionKey and close the channel.
	 */
	protected void closeChannel(SelectionKey selectionKey)
	{
		selectionKey.cancel();
		
		HTTPResponse response = (HTTPResponse) selectionKey.attachment();
		if(response.context instanceof IReleasable)
		{
			if(response.accessPoint != null)
			{
				response.accessPoint.release();
				response.accessPoint = null;
			}
		}
		
		try
		{
			if (server.info)
			{
				System.out.println("HTTP disconnect: " + ((SocketChannel)selectionKey.channel()).getRemoteAddress());
			}
			selectionKey.channel().close();
		}
		catch (IOException e1)
		{
			// game over can't even close a channel now.
			e1.printStackTrace();
		}
	}

	@Override
	public int getDefaultInterestSet()
	{
		return SelectionKey.OP_WRITE;
	}

	@Override
	public void onRegister(SelectionKey key)
	{
		
	}
}
