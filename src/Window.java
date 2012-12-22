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
	
	private JMenu file_menu;
	private JMenu tool_menu;
	private JMenu help_menu;
	
	private JMenuItem[] file_menu_items;
	private JMenuItem[] tool_menu_items;
	private JMenuItem[] help_menu_items;
	
	private JTextArea top_text;
	private JTextArea bottom_text;
	
	private JLabel state_label;
	
	private JButton send_btn;
	private JButton cancel_btn;
	
	ActionController actionhandler;

	private int conn_state;
	
	Server server;
	Client client;
	Thread srv;
	Thread cli;
	int port;

	Window(String name, int port){
		super(name, 300, 440);
		this.port = port;
		this.conn_state=this.CONN_STATE_DISCONNCT;
		actionhandler = new ActionController(this);
		InitMenu();
		InitActionListener();
		actionhandler.updateObserver();
		validate();
		InitServer();
	}
	
	void InitLayout(){
		
		this.setLayout(null);
		
		this.top_text = new JTextArea();
		this.bottom_text = new JTextArea();
		this.send_btn = new JButton("Send");
		this.cancel_btn = new JButton("Cancel");
		
		this.top_text.setBounds(0,0,300,150);
		this.top_text.setEditable(false);
		this.bottom_text.setBounds(0,160,300,150);
		this.send_btn.setBounds(100, 320, 80, 30);
		this.cancel_btn.setBounds(190, 320, 100, 30);
		
		add(this.top_text);
		add(this.bottom_text);
		add(this.send_btn);
		add(this.cancel_btn);
		
	}
	
	void InitMenu(){

		int i;
		
		this.menubar = new JMenuBar();
		
		this.file_menu = new JMenu("File");
		this.tool_menu = new JMenu("Tools");
		this.help_menu = new JMenu("Help");
		
		this.file_menu_items = new JMenuItem[this.FILE_ITEM_COUNT];
		this.file_menu_items[this.FILE_ITEM_CONNECT] = new JMenuItem("Connect");
		this.file_menu_items[this.FILE_ITEM_DISCONNECT] = new JMenuItem("Disconnect");
		this.file_menu_items[this.FILE_ITEM_EXIT] = new JMenuItem("Exit");
		
		this.help_menu_items = new JMenuItem[this.HELP_ITEM_COUNT];
		help_menu_items[this.HELP_ITEM_ABOUT] = new JMenuItem("About");
		
		for (i=0;i<this.file_menu_items.length;i++){
			this.file_menu.add(this.file_menu_items[i]);
		}
		
		for (i=0;i<this.help_menu_items.length;i++){
			this.help_menu.add(this.help_menu_items[i]);
		}
		
		this.menubar.add(this.file_menu);
		this.menubar.add(this.help_menu);
		
		this.updateConnItemState();

		this.setJMenuBar(menubar);
		
	}
	
	void InitActionListener(){
		int i;
		actionhandler.addListen(send_btn);
		actionhandler.addListen(cancel_btn);
		for (i=0;i<this.file_menu_items.length;i++){
			actionhandler.addListen(this.file_menu_items[i]);
		}
		for (i=0;i<this.help_menu_items.length;i++){
			actionhandler.addListen(this.help_menu_items[i]);
		}
	}

	void InitServer(){
		server = new Server(this.port);
		srv = new Thread(server);
		//srv.start();
	}
	
	void clearText(){
		bottom_text.setText("");
	}

	void changeConnState(int x){
		this.conn_state = x;
		this.updateConnItemState();
	}

	void updateConnItemState(){
		if ( this.conn_state == this.CONN_STATE_DISCONNCT ){
			this.file_menu_items[this.FILE_ITEM_CONNECT].setEnabled(true);
			this.file_menu_items[this.FILE_ITEM_DISCONNECT].setEnabled(false);
		}
		else if ( this.conn_state == this.CONN_STATE_CONNCTED ){
			this.file_menu_items[this.FILE_ITEM_CONNECT].setEnabled(false);
			this.file_menu_items[this.FILE_ITEM_DISCONNECT].setEnabled(true);
		}
	}

	JMenuItem getFileItem(int i){
		return this.file_menu_items[i];
	}
	
	JMenuItem getToolItem(int i){
		return this.tool_menu_items[i];
	}
	
	JMenuItem getHelpItem(int i){
		return this.help_menu_items[i];
	}
	
	JButton getSendBtn(){
		return this.send_btn;
	}
	
	JButton getCancelBtn(){
		return this.cancel_btn;
	}


	
}


