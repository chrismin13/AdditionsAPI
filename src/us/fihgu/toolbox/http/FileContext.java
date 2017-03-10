package us.fihgu.toolbox.http;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.LinkedList;

public class FileContext extends HTTPContext implements IReleasable
{
	protected Path path;
	protected SeekableByteChannel fileChannel;
	protected LinkedList<AccessPoint> accessPoints = new LinkedList<AccessPoint>();
	
	public FileContext(Path path)
	{
		this.path = path;
	}
	
	protected void construct()
	{
		try
		{
			this.fileChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	protected void release()
	{
		if(this.fileChannel != null)
		{
			try
			{
				this.fileChannel.close();
				this.fileChannel = null;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public long getSize()
	{
		try
		{
			if(this.fileChannel != null)
			{
				return fileChannel.size();
			}
			else
			{
				return 0;
			}
		}
		catch (IOException e)
		{
			return 0;
		}
	}

	@Override
	public synchronized long read(SocketChannel channel, long position) throws IOException
	{
		if(this.fileChannel != null)
		{
			return ((FileChannel) fileChannel).transferTo(position, this.getSize(), channel);
		}
		else
		{
			return -1;
		}
	}

	@Override
	public AccessPoint aquireAccess()
	{
		AccessPoint accessPoint = new AccessPoint(this);
		this.accessPoints.add(accessPoint);
		
		if(this.fileChannel == null)
		{
			this.construct();
		}
		
		return accessPoint;
	}

	@Override
	public void removeAccessPoint(AccessPoint accessPoint)
	{
		this.accessPoints.remove(accessPoint);
		
		if(this.accessPoints.size() <= 0)
		{
			this.release();
		}
	}

}
