import java.io.*;
import java.net.*;

public class Client implements Runnable{
	
	private String host;
	private int port;
	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;
	private Window parentWindow;
	
	Client(String host, int port){
		this.host = host;
		this.port = port;
		this.in = null;
		this.out = null;
	}

	void Connect(){
		try{
			this.parentWindow.AppendInfo("Connecting to server: " + host + ":" + port );
			this.client = new Socket(this.host, this.port);
			this.in = new DataInputStream(this.client.getInputStream());
			this.out = new DataOutputStream(this.client.getOutputStream());
			InetAddress address = InetAddress.getLocalHost();
			this.Send("Connected by : " + address.toString());
			this.parentWindow.AppendInfo("Connecting to server: " + host + ":" + port +" completed!");
		}
		catch (IOException e){
			this.parentWindow.AppendInfo("Cannot connect to the server!");
		}
	}
	
	void Send(String info){
		try {
			this.out.writeUTF(info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	String Receive(){
		try{
			String info = null;
			info = this.in.readUTF();
			return info;
		}catch(IOException e){
			return null;
		}
	}

	void setParentWindow(Window parent){
		this.parentWindow = parent;
	}

	@Override
	public void run(){
		while(true){
			this.parentWindow.AppendInfo(this.Receive());
		}
	}
}
