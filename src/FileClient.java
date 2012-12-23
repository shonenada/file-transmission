import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;

public class FileClient{
	
	private String host;
	private int filePort;
	private Socket fileClient;
	private BufferedInputStream fileBufferIn;
	private BufferedOutputStream fileBufferOut;
	private FileInputStream fileIn;
	private Window parentWindow;
	private boolean isConnect;
	JFileChooser chooser;
	String filename;
	
	FileClient(String host, int port){
		this.host = host;
		this.filePort = port;
		this.isConnect = false;
		this.fileBufferIn = null;
		this.fileBufferOut = null;
	}

	void Connect(){
		try{
			this.parentWindow.AppendInfo("Connecting to server: " + this.host + ":" + this.filePort );
			this.fileClient = new Socket(this.host, this.filePort);
			this.fileBufferIn = new BufferedInputStream(this.fileClient.getInputStream());
			this.fileBufferOut = new BufferedOutputStream(this.fileClient.getOutputStream());
			this.isConnect = true;
			this.parentWindow.changeConnState(Window.CONN_STATE_CONNCTED);
			this.parentWindow.AppendInfo("Connecting to server: " + this.host + ":" + this.filePort +" completed!");
		}
		catch (IOException e){
			this.parentWindow.AppendInfo("Cannot connect to the server!");
			this.parentWindow.changeConnState(Window.CONN_STATE_DISCONNCT);
		}
	}

	void DisConnect(){
		try{	
			this.fileBufferIn.close();
			this.fileBufferOut.close();
			this.fileClient.close();
			this.isConnect = false;
			this.fileBufferIn = null;
			this.fileBufferOut = null;
			this.fileClient = null;
			this.parentWindow.changeConnState(Window.CONN_STATE_DISCONNCT);
		}
		catch ( IOException e ){	
			System.out.println(e.toString());
		}

	}

	void Send(){
		try {
			int chval = this.chooser.showSaveDialog(this.parentWindow);
			if (chval == JFileChooser.APPROVE_OPTION){
				this.filename = chooser.getSelectedFile().getAbsolutePath();
				File file = new File(filename);
				fileIn = new FileInputStream(file);
				fileBufferIn = new BufferedInputStream(fileIn);
				byte[] buff = new byte[2048];
				int num = fileBufferIn.read(buff);
				while( num != -1 ){
					fileBufferOut.write(buff, 0, num);
					fileBufferOut.flush();
					num = fileBufferIn.read(buff);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch ( IOException e ){}
	}

	void setParentWindow(Window parent){
		this.parentWindow = parent;
	}
}
