package object;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Host
{
	String hostname = null;
	InetAddress addr;
	
	public Host(String hostname)
	{
		this.hostname=hostname;
		try {
			addr=InetAddress.getByName(hostname);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Host()
	{
	
		try {
			addr=InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) 
	{
		Host tmp=(Host)obj;
		return (this.getAddr().equals(tmp.getAddr()) && this.hostname.equals(tmp.getHostname()));
	}

	public String getHostname() {
		return hostname;
	}

	public InetAddress getAddr() {
		return addr;
	}
}
