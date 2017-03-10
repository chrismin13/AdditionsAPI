package us.fihgu.toolbox.web;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * A thread that holds one selector with a given SelectionHandler<br>
 */
public class SelectorThread extends Thread
{	
	protected Selector selector;
	private SelectionHandler selectionHandler;
	private boolean isStopping = false;
	private boolean isStopped = false;
	private boolean isBusy = false;
	
	private Object registerLock = new Object();
	
	public SelectorThread(SelectionHandler selectionHandler) throws IOException
	{
		selector = Selector.open();
		
		if(selectionHandler == null)
		{
			throw new NullPointerException("selectionHandler must not be null");
		}
		
		this.selectionHandler = selectionHandler;
	}

	@Override
	public void run()
	{
		while(!this.isStopping)
		{
			try
			{
				int numKeys = this.selector.select();
				this.isBusy = true;
				
				synchronized(this.registerLock)
				{
					
				}
				
				if(numKeys > 0)
				{
					Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectedKeys.iterator();
					while(iterator.hasNext())
					{
						SelectionKey selectionKey = iterator.next();
						this.selectionHandler.handleSelection(selectionKey);
						iterator.remove();
					}
				}
				
				this.isBusy = false;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		this.onClose();
	}
	
	/**
	 * @return true if this Selector is valid to register more SocketChannels.
	 */
	public boolean isValid()
	{
		return !this.isStopping;
	}
	
	public void closeSelector()
	{
		this.isStopping = true;
		this.selector.wakeup();
	}
	
	public SelectionKey register(SelectableChannel channel, Object attachment) throws ClosedChannelException
	{
		
		SelectionKey result;
		synchronized(this.registerLock)
		{
			this.selector.wakeup();
			result = channel.register(this.selector, this.selectionHandler.getDefaultInterestSet(), attachment);
			
		}
		this.getSelectionHandler().onRegister(result);
		return result;
	}
	
	public SelectionKey register(SelectableChannel channel) throws ClosedChannelException
	{
		return this.register(channel, null);
	}
	
	/**
	 * @return the number of SelectionKeys registered to this selector
	 */
	public int getKeySize()
	{
		return this.selector.keys().size();
	}
	
	private void onClose()
	{
		try
		{
			this.selector.close();
			this.selector = null;
			
			synchronized(this)
			{
				this.isStopped = true;
				this.notify();
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean isStopped()
	{
		return this.isStopped;
	}
	
	public SelectionHandler getSelectionHandler()
	{
		return this.selectionHandler;
	}
	
	/**
	 * returns true if this selector is currently processing some work load.
	 */
	public boolean isBusy()
	{
		return this.isBusy;
	}
}
