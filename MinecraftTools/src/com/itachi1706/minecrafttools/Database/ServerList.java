package com.itachi1706.minecrafttools.Database;

import java.net.InetSocketAddress;

public class ServerList {
	
	private int mid;
	private InetSocketAddress maddress;
	private String mVersion;
	private String mName;
	
	public ServerList(){}
	
	//Initial adding
	public ServerList(InetSocketAddress address, String version, String name){
		this.maddress=address;
		this.mVersion=version;
		this.mName=name;
	}
	
	//Via InetSocketAddr
	public ServerList(int id, InetSocketAddress address, String version, String name){
		this.mid=id;
		this.maddress=address;
		this.mVersion=version;
		this.mName=name;
	}
	
	//Via Hostname and Port
	public ServerList(int id, String ip, int port, String version, String name){
		this.mid=id;
		this.maddress=new InetSocketAddress(ip,port);
		this.mVersion=version;
		this.mName=name;
	}
	
	//Via Hostname and Port wo name
	public ServerList(int id, String ip, int port, String version){
		this.mid=id;
		this.maddress=new InetSocketAddress(ip,port);
		this.mVersion=version;
		this.mName=null;
	}
	
	//Via InetSocketAddr
	public ServerList(int id, InetSocketAddress address, String version){
		this.mid=id;
		this.maddress=address;
		this.mVersion=version;
		this.mName=null;
	}
	
	public int getId(){return this.mid;}
	public void setId(int id){this.mid=id;}
	
	public InetSocketAddress getAddress(){return this.maddress;}
	public void setAddress(InetSocketAddress address){this.maddress=address;}
	public void setAddress(String ip, int port){this.maddress=new InetSocketAddress(ip,port);}
	
	public String getVersion(){return this.mVersion;}
	public void setVersion(String version){this.mVersion=version;}
	
	public String getName(){return this.mName;}
	public void setName(String name){this.mName=name;}
	
	public String toString(){return this.mid + "," + this.maddress.getHostName() + 
			"," + this.maddress.getPort() + "," + this.mVersion + "," + this.mName;
	}
	

}
