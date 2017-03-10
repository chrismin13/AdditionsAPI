package us.fihgu.toolbox.web;

import java.nio.channels.SelectionKey;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class TimerThread extends Thread
{

	private boolean isRunning = true;
	private WebServer server;
	
	private HashMap<SelectionKey, Long> dueTimes = new HashMap<SelectionKey, Long>();
	
	public TimerThread(WebServer server)
	{
		this.server = server;
	}
	
	@Override
	public void run()
	{
		while(isRunning)
		{
			synchronized(this.dueTimes)
			{
				long now = Calendar.getInstance().getTimeInMillis();
				Iterator<SelectionKey> iterator = dueTimes.keySet().iterator();
				while(iterator.hasNext())
				{
					SelectionKey key = iterator.next();
					Long dueTime = dueTimes.get(key);
					if(now >= dueTime)
					{
						iterator.remove();
						server.onTimeOut(key);
					}
				}
			}
			
			
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void putTimer(SelectionKey selectionKey, long timeOutTime)
	{
		synchronized(this.dueTimes)
		{
			this.dueTimes.put(selectionKey, timeOutTime);
		}
	}
	
	public void removeTimer(SelectionKey selectionKey)
	{
		synchronized(this.dueTimes)
		{
			this.dueTimes.remove(selectionKey);
		}
	}
	
	public void stopThread()
	{
		this.isRunning = false;
	}
}
