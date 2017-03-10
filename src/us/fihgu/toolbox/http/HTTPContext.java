package us.fihgu.toolbox.http;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * a HTTPContext is a resource that's related to a web path <br>
 * HTTPContext has a few important properties:<br>
 * It is thread safe.<br>
 * Any Thread can read from a random position of the resource at any time.<br>
 * @author fihgu
 *
 */
public abstract class HTTPContext
{
	protected String path;
	
	public abstract long getSize();
	
	/**
	 * read as much bytes as possible, from the resource to the given channel.
	 * @param channel the channel to write to
	 * @param contextPosition the beginning position to read, inclusive.
	 * @return the number of bytes actually read
	 * @throws IOException
	 */
	public abstract long read(SocketChannel channel, long contextPosition) throws IOException;
	
	public String getPath()
	{
		return this.path;
	}
}
