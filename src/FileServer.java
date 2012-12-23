import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;

public class FileServer implements Runnable {
	private ServerSocket fileServer;
	private Socket fileClient;
	private BufferedInputStream fileBufferIn;
	private BufferedOutputStream fileBufferOut;
	private FileOutputStream fileOut;
	private Window parentWindow;
	private String remoteIP;
	private int filePort;
	private boolean isRun;
	JFileChooser chooser;
	String filename;

	FileServer(int filePort){
		this.fileClient = null;
		this.filePort = filePort;
		this.chooser = new JFileChooser();
	}

	public void start(){
		try{
			this.fileServer = new ServerSocket(filePort);
			this.fileClient = this.fileServer.accept();
			remoteIP = this.fileClient.getInetAddress().toString().replace("/", "");
			this.parentWindow.AppendInfo("Connected by: " + remoteIP);
			this.isRun = true;
			if( !this.parentWindow.isConncted() ){
				this.parentWindow.ConnectToServer(remoteIP);
			}
			fileBufferIn = new BufferedInputStream(fileClient.getInputStream());
		}
		catch(IOException e){
			System.out.println("ERRO: " + e);
		}
	}

	public void stop(){
		try{
			this.isRun = false;
			this.fileBufferIn = null;
			this.fileOut = null;
			this.fileServer.close();
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
			if ( !this.isRun ){
				this.start();
				break;
			}
		}
	}

	@Override
	public void run(){
		try{
			byte[] msg = new byte[2048];
			String temp;
			while(this.isRun){
				int num = this.fileBufferIn.read(msg);
				if( ! (num != -1) ) {
					if ( this.filename == null || this.filename.equals("") ){
						System.out.println("Server");
						int chval = this.chooser.showSaveDialog(this.parentWindow);
						if (chval == JFileChooser.APPROVE_OPTION){
							temp = this.filename;
							this.filename = chooser.getSelectedFile().getAbsolutePath();
						}
					}
					File file = new File(filename);
					this.fileOut = new FileOutputStream(file);
					this.fileBufferOut = new BufferedOutputStream(fileOut);
					while ( num != -1 ){
						fileBufferOut.write(msg, 0, num);
						fileBufferOut.flush();
						num = this.fileBufferIn.read(msg);
					}
					fileBufferOut.close();
					fileOut.close();
					this.fileBufferIn.close();
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	

	void setParentWindow(Window parent){
		this.parentWindow = parent;
	}
}
