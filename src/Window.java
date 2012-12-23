import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class Window extends DFrame {

	final static int FILE_ITEM_COUNT=3;
	final static int TOOL_ITEM_COUNT=1;
	final static int HELP_ITEM_COUNT=1;
	final static int FILE_ITEM_CONNECT=0;
	final static int FILE_ITEM_DISCONNECT=1;
	final static int FILE_ITEM_EXIT=2;
	final static int TOOL_ITEM_SENDFILE=0;
	final static int HELP_ITEM_ABOUT=0;
	final static int SRV_STATE_RUN=1;
	final static int SRV_STATE_STOP=0;
	final static int CONN_STATE_CONNCTED=1;
	final static int CONN_STATE_DISCONNCT=0;
	final static int CONN_CHAT_PORT=4331;
	final static int CONN_FILE_PORT=4332;

	private JMenuBar menubar;
	
	private JMenu fileMenu;
	private JMenu toolMenu;
	private JMenu helpMenu;
	
	private JMenuItem[] fileMenuItems;
	private JMenuItem[] toolMenuItems;
	private JMenuItem[] helpMenuItems;
	
	private JTextArea topText;
	private JTextArea bottomText;
	JScrollPane topPane;
	JScrollPane bottomPane;
	
	private JLabel stateLabel;
	
	private JButton sendBtn;
	private JButton cancelBtn;
	
	ActionController actionhandler;

	private int connState;
	private String username;
	
	ChatServer chatServer;
	ChatClient chatClient;
	FileServer fileServer;
	FileClient fileClient;
	Thread chatSrv;
	Thread chatCli;
	Thread fileSrv;
	Thread fileCli;

	Window(String name){
		super(name, 390, 460);
		this.connState=this.CONN_STATE_DISCONNCT;
		actionhandler = new ActionController(this);
		InitMenu();
		InitActionListener();
		actionhandler.updateObserver();
		validate();
		InitServer();
	}
	
	@Override
	void InitLayout(){
		
		this.setLayout(null);
		
		this.topText = new JTextArea();
		this.bottomText = new JTextArea();
		this.sendBtn = new JButton("Send");
		this.cancelBtn = new JButton("Cancel");
		this.topPane = new JScrollPane(topText);
		this.bottomPane = new JScrollPane(bottomText);

		this.topText.setLineWrap(true);
		this.bottomText.setLineWrap(true);

		this.topPane.setBounds(10,10,370,200);
		this.topText.setEditable(false);
		this.bottomPane.setBounds(10,230,370,100);
		this.sendBtn.setBounds(100, 360, 80, 30);
		this.cancelBtn.setBounds(190, 360, 100, 30);

		this.sendBtn.setMnemonic(KeyEvent.VK_S);
		
		add(this.topPane);
		add(this.bottomPane);
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
		
		this.toolMenuItems = new JMenuItem[this.TOOL_ITEM_COUNT];
		this.toolMenuItems[this.TOOL_ITEM_SENDFILE] = new JMenuItem("Send file");

		this.helpMenuItems = new JMenuItem[this.HELP_ITEM_COUNT];
		helpMenuItems[this.HELP_ITEM_ABOUT] = new JMenuItem("About");
		
		for (i=0;i<this.fileMenuItems.length;i++){
			this.fileMenu.add(this.fileMenuItems[i]);
		}
		
		for (i=0;i<this.toolMenuItems.length;i++){
			this.toolMenu.add(this.toolMenuItems[i]);
		}

		for (i=0;i<this.helpMenuItems.length;i++){
			this.helpMenu.add(this.helpMenuItems[i]);
		}
		
		this.menubar.add(this.fileMenu);
		this.menubar.add(this.toolMenu);
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
		for (i=0;i<this.toolMenuItems.length;i++){
			actionhandler.addListen(this.toolMenuItems[i]);
		}
		for (i=0;i<this.helpMenuItems.length;i++){
			actionhandler.addListen(this.helpMenuItems[i]);
		}
	}

	void InitServer(){
		try{
			InetAddress local = InetAddress.getLocalHost();
			this.username = local.getHostName().toString();
		}
		catch(UnknownHostException e){}
		chatServer = new ChatServer(Window.CONN_CHAT_PORT);
		fileServer = new FileServer(Window.CONN_FILE_PORT);
		chatServer.setParentWindow(this);
		fileServer.setParentWindow(this);
		chatServer.start();
	//	fileServer.start();
		chatSrv = new Thread(chatServer);
	//	fileSrv = new Thread(fileServer);
		chatSrv.start();
	//	fileSrv.start();
	}

	void ConnectToServer(String host){
		// #TODO 
		chatClient = new ChatClient(host, Window.CONN_CHAT_PORT);
		chatClient.setParentWindow(this);
		chatClient.Connect();
		chatCli = new Thread(chatClient);
		chatCli.start();
	}

	void DisConnect(){
		if ( this.isConncted() ){
			this.AppendInfo("Disconnecting from the server.");
			this.chatClient.DisConnect();
			this.AppendInfo("Disconnecting from the server completed!");	
		}
	}
	
	void clearText(){
		bottomText.setText("");
	}

	void changeConnState(int x){
		this.connState = x;
		this.updateConnItemState();
	}

	String getBottomText(){
		return this.bottomText.getText();
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

	void AppendInfo(String info){
		String temp = this.topText.getText();
		this.topText.setText(temp+"\n"+info);
	}

	boolean isConncted(){
		return this.connState == this.CONN_STATE_CONNCTED;
	}

	void setUsername(String name){
		this.username = name;
	}

	String getUsername(){
		return this.username;
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


