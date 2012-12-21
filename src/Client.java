import java.io.*;
import java.net.*;

public class Client {
	
	private String s;
	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;;
	
	Client(int port){
		s = null;
		in = null;
		out = null;
		try{
			this.client = new Socket("localhost", port);
			in = new DataInputStream(this.client.getInputStream());
			out = new DataOutputStream(this.client.getOutputStream());
		}
		catch (IOException e){}
	}
	
	void Send(String info){
		try {
			this.out.writeUTF(info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	String Receive(){
		String info = null;
		try{
			info = this.in.readUTF();
		}catch(IOException e){
			
		}
		return info;
	}
}
