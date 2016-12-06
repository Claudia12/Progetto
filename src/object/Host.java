package object;


public class Host
{
	String hostname = null;
	
	
	public Host(String hostname)
	{
		this.hostname=hostname;
	
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj==null || !(obj instanceof Host))
		{
		return false;
		}
		Host tmp=(Host)obj;
		return this.hostname.equals(tmp.getHostname());
	}

	public String getHostname() {
		return hostname;
	}


	@Override
	public String toString() 
	{
	
	return hostname; 
	}
	
}
