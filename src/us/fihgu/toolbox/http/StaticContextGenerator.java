package us.fihgu.toolbox.http;


public class StaticContextGenerator implements IContextGenerator
{
	HTTPContext context;
	public StaticContextGenerator(HTTPContext context)
	{
		this.context = context;
	}
	
	@Override
	public HTTPContext generateContext(HTTPRequest request)
	{
		return context;
	}
}
