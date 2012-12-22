import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Window extends DFrame {

	final static int FILE_ITEM_COUNT=3;
	final static int HELP_ITEM_COUNT=1;
	final static int FILE_ITEM_CONNECT=0;
	final static int FILE_ITEM_DISCONNECT=1;
	final static int FILE_ITEM_EXIT=2;
	final static int HELP_ITEM_ABOUT=0;
	final static int SRV_STATE_RUN=1;
	final static int SRV_STATE_STOP=0;
	final static int CONN_STATE_CONNCTED=1;
	final static int CONN_STATE_DISCONNCT=0;
	final static int CONN_PORT=4331;

	private JMenuBar menubar;
	
	private JMenu fileMenu;
	private JMenu toolMenu;
	private JMenu helpMenu;
	
	private JMenuItem[] fileMenuItems;
	private JMenuItem[] toolMenuItems;
	private JMenuItem[] helpMenuItems;
	
	private JTextArea topText;
	private JTextArea bottomText;
	
	private JLabel stateLabel;
	
	private JButton sendBtn;
	private JButton cancelBtn;
	
	ActionController actionhandler;

	private int connState;
	
	Server server;
	Client client;
	Thread srv;
	Thread cli;
	int port;

	Window(String name, int port){
		super(name, 300, 440);
		this.port = port;
		this.connState=this.CONN_STATE_DISCONNCT;
		actionhandler = new ActionController(this);
		InitMenu();
		InitActionListener();
		actionhandler.updateObserver();
		validate();
		InitServer();
	}
	
	void InitLayout(){
		
		this.setLayout(null);
		
		this.topText = new JTextArea();
		this.bottomText = new JTextArea();
		this.sendBtn = new JButton("Send");
		this.cancelBtn = new JButton("Cancel");
		
		this.topText.setBounds(0,0,300,150);
		this.topText.setEditable(false);
		this.bottomText.setBounds(0,160,300,150);
		this.sendBtn.setBounds(100, 320, 80, 30);
		this.cancelBtn.setBounds(190, 320, 100, 30);
		
		add(this.topText);
		add(this.bottomText);
		add(this.sendBtn);
		add(this.cancelBtn);
		
	}
	
	void InitMenu(){

		int i;
		
		this.menubar = new JMenuBar();
		
		this.fileMenu = new JMenu("File");
		this.toolMenu = new JMenu("Tools");
		this.helpMenu = new JMenu("Help");
		
		this.fileMenuItems = new JMenuItem[this.FILE_ITEM_COUNT];
		this.fileMenuItems[this.FILE_ITEM_CONNECT] = new JMenuItem("Connect");
		this.fileMenuItems[this.FILE_ITEM_DISCONNECT] = new JMenuItem("Disconnect");
		this.fileMenuItems[this.FILE_ITEM_EXIT] = new JMenuItem("Exit");
		
		this.helpMenuItems = new JMenuItem[this.HELP_ITEM_COUNT];
		helpMenuItems[this.HELP_ITEM_ABOUT] = new JMenuItem("About");
		
		for (i=0;i<this.fileMenuItems.length;i++){
			this.fileMenu.add(this.fileMenuItems[i]);
		}
		
		for (i=0;i<this.helpMenuItems.length;i++){
			this.helpMenu.add(this.helpMenuItems[i]);
		}
		
		this.menubar.add(this.fileMenu);
		this.menubar.add(this.helpMenu);
		
		this.updateConnItemState();

		this.setJMenuBar(menubar);
		
	}
	
	void InitActionListener(){
		int i;
		actionhandler.addListen(sendBtn);
		actionhandler.addListen(cancelBtn);
		for (i=0;i<this.fileMenuItems.length;i++){
			actionhandler.addListen(this.fileMenuItems[i]);
		}
		for (i=0;i<this.helpMenuItems.length;i++){
			actionhandler.addListen(this.helpMenuItems[i]);
		}
	}

	void InitServer(){
		server = new Server(this.port);
		srv = new Thread(server);
		srv.start();
	}

	void clearText(){
		bottomText.setText("");
	}

	void changeConnState(int x){
		this.connState = x;
		this.updateConnItemState();
	}

	void updateConnItemState(){
		if ( this.connState == this.CONN_STATE_DISCONNCT ){
			this.fileMenuItems[this.FILE_ITEM_CONNECT].setEnabled(true);
			this.fileMenuItems[this.FILE_ITEM_DISCONNECT].setEnabled(false);
		}
		else if ( this.connState == this.CONN_STATE_CONNCTED ){
			this.fileMenuItems[this.FILE_ITEM_CONNECT].setEnabled(false);
			this.fileMenuItems[this.FILE_ITEM_DISCONNECT].setEnabled(true);
		}
	}

	JMenuItem getFileItem(int i){
		return this.fileMenuItems[i];
	}
	
	JMenuItem getToolItem(int i){
		return this.toolMenuItems[i];
	}
	
	JMenuItem getHelpItem(int i){
		return this.helpMenuItems[i];
	}
	
	JButton getSendBtn(){
		return this.sendBtn;
	}
	
	JButton getCancelBtn(){
		return this.cancelBtn;
	}


	
}


