import java.io.*;
import java.net.*;

public class ChatServer implements Runnable {
	private ServerSocket chatServer;
	private	Socket chatClient;
	private DataOutputStream chatOut;
	private DataInputStream chatIn;
	private Window parentWindow;
	private String remoteIP;
	private int chatPort;
	private boolean isRun;
	
	ChatServer(int chatPort){
		this.chatClient = null;
		this.chatPort = chatPort;
	}

	public void start(){
		try{
			this.chatServer = new ServerSocket(chatPort);
			this.chatClient = this.chatServer.accept();
			remoteIP = this.chatClient.getInetAddress().toString().replace("/", "");
			this.parentWindow.AppendInfo("Connected by: " + remoteIP);
			this.isRun = true;
			if( !this.parentWindow.isConncted() ){
				this.parentWindow.ConnectToServer(remoteIP);
			}
			chatIn = new DataInputStream(chatClient.getInputStream());
			chatOut = new DataOutputStream(chatClient.getOutputStream());
		}
		catch(IOException e){
			System.out.println("ERRO: " + e);
		}
	}

	public void stop(){
		try{
			this.isRun = false;
			this.chatIn = null;
			this.chatOut = null;
			this.chatServer.close();
		}
		catch(IOException e){
			System.out.println("ERROR: " + e);
		}
	}

	void restart(){
		while(true){
			if ( this.isRun ){
				this.stop();
			}
			if ( !this.isRun){
				this.start();
				break;
			}
		}
	}

	@Override
	public void run(){
		try{
			while(this.isRun){
				String msg;
				msg = chatIn.readUTF();
				if(msg == null || chatIn == null){
					this.parentWindow.DisConnect();
					break;
				}
				this.parentWindow.AppendInfo(msg);
			}
		}
		catch(IOException e){}
	}

	void setParentWindow(Window parent){
		this.parentWindow = parent;
	}
}
