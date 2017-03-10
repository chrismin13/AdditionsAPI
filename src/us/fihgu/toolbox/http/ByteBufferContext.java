package us.fihgu.toolbox.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ByteBufferContext extends HTTPContext
{
	
	protected ByteBuffer buffer;
	
	public ByteBufferContext(ByteBuffer buffer)
	{
		this.buffer = buffer;
	}

	@Override
	public long getSize()
	{
		return buffer.limit();
	}

	@Override
	public synchronized long read(SocketChannel channel, long position) throws IOException
	{
		if(position >= this.getSize())
		{
			throw new IOException();
		}
		
		buffer.position((int)position);
		return channel.read(buffer);
	}
}
