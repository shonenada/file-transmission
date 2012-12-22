import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ActionController implements ActionListener{
	
	private Window target;
	private JMenuItem[] menuItemObservers;
	private JButton[] buttonObservers;
	private int menuIndex;
	private int btnIndex;
	
	ActionController(Window t){
		this.target = t;
		this.menuItemObservers = new JMenuItem[100];
		this.buttonObservers = new JButton[100];
		this.menuIndex=0;
		this.btnIndex=0;
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		// Exit the window
		if ( src == target.getFileItem(Window.FILE_ITEM_EXIT) ){
			target.dispose();
			System.exit(0);
		}
		
		// Show About window
		if ( src == target.getHelpItem(Window.HELP_ITEM_ABOUT) ){
			new AboutWindow();
		}

		// Connect to a server
		if( src == target.getFileItem(Window.FILE_ITEM_CONNECT) ){
			new ConnectWindow(target);
		}

		// Disconnect
		if ( src == target.getFileItem(Window.FILE_ITEM_DISCONNECT) ){
			target.DisConnect();
		}

		// Send, send info to remote server.
		if ( src == target.getSendBtn() ){
			this.target.client.Send(this.target.getBottomText());
		}

		// Cancel, close the window .
		if ( src == target.getCancelBtn() ){
			target.dispose();
			System.exit(0);
		}

	}	
	
	public void addListen(JMenuItem o){
		this.menuItemObservers[this.menuIndex++] = o;
	}
	
	public void addListen(JButton o){
		this.buttonObservers[this.btnIndex++] = o;
	}
	
	public void updateObserver(){
		int i;
		for(i=0;i<this.menuIndex;i++){
			this.menuItemObservers[i].addActionListener(this);
		}
		for(i=0;i<this.btnIndex;i++){
			this.buttonObservers[i].addActionListener(this);
		}
	}

}
