import java.io.*;
import java.net.*;

public class Server implements Runnable {
	private ServerSocket server;
	private	Socket client;
	private DataOutputStream out;
	private DataInputStream in;
	
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
		catch(IOException e){
			System.out.println("Cannot connect to the server.");
		}
	}

	public void run(){
		try{
			while(true){
				String m;
				m = in.readUTF();
				out.writeUTF(m);
				System.out.println("服务器收到: " + m);
				Thread.sleep(500);
			}
		}catch(InterruptedException e){}
		catch(IOException e){
			System.out.println("Cannot connect to the server.");
		}
	}
}
