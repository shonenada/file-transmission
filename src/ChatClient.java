import java.io.*;
import java.net.*;

public class ChatClient implements Runnable{
	
	private String host;
	private int chatPort;
	private Socket chatClient;
	private DataInputStream chatIn;
	private DataOutputStream chatOut;
	private Window parentWindow;
	private boolean isConnect;
	
	ChatClient(String host, int port){
		this.host = host;
		this.chatPort = port;
		this.isConnect = false;
		this.chatIn = null;
		this.chatOut = null;
	}

	void Connect(){
		try{
			this.parentWindow.AppendInfo("Connecting to server: " + this.host + ":" + this.chatPort );
			this.chatClient = new Socket(this.host, this.chatPort);
			this.chatIn = new DataInputStream(this.chatClient.getInputStream());
			this.chatOut = new DataOutputStream(this.chatClient.getOutputStream());
			this.isConnect = true;
			this.parentWindow.changeConnState(Window.CONN_STATE_CONNCTED);
			this.parentWindow.AppendInfo("Connecting to server: " + this.host + ":" + this.chatPort +" completed!");
		}
		catch (IOException e){
			this.parentWindow.AppendInfo("Cannot connect to the server!" + this.host + ":" + this.chatPort);
			this.parentWindow.changeConnState(Window.CONN_STATE_DISCONNCT);
		}
	}

	void DisConnect(){
		try{	
			this.chatIn.close();
			this.chatOut.close();
			this.chatClient.close();
			this.isConnect = false;
			this.chatIn = null;
			this.chatOut = null;
			this.chatClient = null;
			this.parentWindow.changeConnState(Window.CONN_STATE_DISCONNCT);
		}
		catch ( IOException e ){	
			System.out.println(e.toString());
		}

	}

	void Send(String info){
		try {
			this.parentWindow.AppendInfo("You said: " + info);
			this.chatOut.writeUTF(this.parentWindow.getUsername() + " said: " + info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	String Receive(){
		try{
			if (this.chatIn == null){
				return null;
			}
			String info = null;
			info = this.chatIn.readUTF();
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
		while(this.isConnect){
			String reci = this.Receive();
			if(reci == null){
				this.parentWindow.DisConnect();
				break;
			}
			this.parentWindow.AppendInfo(reci);
		}
	}
}
