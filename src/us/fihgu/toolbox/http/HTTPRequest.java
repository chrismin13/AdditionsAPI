package us.fihgu.toolbox.http;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class HTTPRequest
{
	protected ByteBuffer readBuffer;
	protected StringBuilder lineBuilder;
	
	public HTTPRequest(HTTPServer server)
	{
		readBuffer = ByteBuffer.allocate(server.readBufferSize);
		lineBuilder = new StringBuilder(server.lineBuilderSize);
	}
	
	public HTTPRequestMethod method;
	public String path;
	public String version;
	
	public HashMap<String, String> headers = new HashMap<String, String>();
	public ByteBuffer body;
	
	@Override
	public String toString()
	{
		String text = this.method.name() + " " + this.path + " " + this.version + "\r\n";
		for(String key : this.headers.keySet())
		{
			String value = this.headers.get(key);
			text += key + ": " + value + "\r\n";
		}
		text += "\r\n";
		text += "body -> " + this.body;
		
		return text;
	}
}
