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

	FileServer(Window parent, int filePort){
		this.fileClient = null;
		this.filePort = filePort;
		this.parentWindow = parent;
		this.chooser = new JFileChooser();
		try{
			this.fileServer = new ServerSocket(filePort);
			this.fileClient = this.fileServer.accept();
			this.isRun = true;
			fileBufferIn = new BufferedInputStream(fileClient.getInputStream());
		}
		catch(IOException e){
			System.out.println("ERRO: " + e);
		}
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run(){
		try{
			byte[] msg = new byte[2048];
			String temp;
			int num;
			while(this.isRun){
				num = this.fileBufferIn.read(msg);
				if( ! (num == -1) ) {
					if ( this.filename == null || this.filename.equals("") ){
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
	
}
