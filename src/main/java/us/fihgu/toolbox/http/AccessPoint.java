package us.fihgu.toolbox.http;

public class AccessPoint
{
	private IReleasable resource;
	
	protected AccessPoint(IReleasable resource)
	{
		this.resource = resource;
	}
	
	public void release()
	{
		if(this.resource != null)
		{
			this.resource.removeAccessPoint(this);
			this.resource = null;
		}
	}
}
