package us.fihgu.toolbox.http;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class HTTPResponse
{
	private static DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
	
	protected ByteBuffer buffer;
	protected AccessPoint accessPoint;
	protected long contextPosition = 0;
	
	public String version = "HTTP/1.1";
	public int code = 200;
	public String reason = "OK";
	
	public HashMap<String, String> headers = new HashMap<String, String>();
	
	public HTTPContext context;
	
	public HTTPResponse(ResponseCode code, HTTPContext context)
	{
		if(code != null)
		{
			this.code = code.getCode();
			this.reason = code.toString();
		}
		
		this.context = context;
		
		//default headers
		String time = dateFormat.format(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());
		headers.put("Date", time);
		headers.put("Server", "fihgu's HTTP Server/1.0");
		headers.put("Connection", "close");
		
		if(this.context != null)
		{
			if(context instanceof IReleasable)
			{
				if(accessPoint == null)
				{
					IReleasable releasable = (IReleasable)context;
					this.accessPoint = releasable.aquireAccess();
				}
			}
			
			headers.put("Content-Length", "" + context.getSize());
		}
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder(this.getHeaderString());
		
		builder.append("Context -> ").append(this.context);
		
		return builder.toString();
	}
	
	/**
	 * 
	 * @return the header of this response, ready to be encoded and sent.
	 */
	public String getHeaderString()
	{
		StringBuilder builder = new StringBuilder(128);
		
		builder.append(this.version).append(" ").append(code).append(" ").append(reason).append("\r\n");
		for(String key: this.headers.keySet())
		{
			String value = this.headers.get(key);
			builder.append(key).append(": ").append(value).append("\r\n");
		}
		builder.append("\r\n");
		
		
		
		return builder.toString();
	}
}
