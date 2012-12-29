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
	
	ChatServer(Window parent, int chatPort){
		this.chatClient = null;
		this.chatPort = chatPort;
		this.parentWindow = parent;
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
			Thread thread = new Thread(this);
			thread.start();
		}
		catch(IOException e){
			System.out.println("ERRO: " + e);
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
}
