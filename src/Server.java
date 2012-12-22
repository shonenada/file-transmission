import java.io.*;
import java.net.*;

public class Server implements Runnable {
	private ServerSocket server;
	private	Socket client;
	private DataOutputStream out;
	private DataInputStream in;
	private Window parentWindow;
	
	Server(int port){
		this.client = null;
		try{
			this.server = new ServerSocket(port);
		}catch(IOException e1){
			System.out.println("ERRO: " + e1);
		}
		try{
			this.client = this.server.accept();
			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());
		}
		catch(IOException e){}
	}

	@Override
	public void run(){
		try{
			while(true){
				String msg;
				msg = in.readUTF();
				this.parentWindow.AppendInfo(msg);
				Thread.sleep(500);
			}
		}catch(InterruptedException e){}
		catch(IOException e){}
	}

	void setParentWindow(Window parent){
		this.parentWindow = parent;
	}
}
