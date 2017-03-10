package us.fihgu.toolbox.http;

/**
 * thrown when a HTTPRequest is like 
 */
public class BadRequestException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public BadRequestException()
	{
		super();
	}
	
	public BadRequestException(String note)
	{
		super(note);
	}
}
