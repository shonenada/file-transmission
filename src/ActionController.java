import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ActionController implements ActionListener{
	
	private Window target;
	private JMenuItem[] MenuItem_observers;
	private JButton[] Button_observers;
	private int Menu_o_i;
	private int Btn_o_i;
	
	ActionController(Window t){
		this.target = t;
		this.MenuItem_observers = new JMenuItem[100];
		this.Button_observers = new JButton[100];
		this.Menu_o_i=0;
		this.Btn_o_i=0;
		
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

		}

		// Cancel, close the window .
		if ( src == target.getCancelBtn() ){
			target.dispose();
			System.exit(0);
		}
	}	
	
	public void addListen(JMenuItem o){
		this.MenuItem_observers[this.Menu_o_i++] = o;
	}
	
	public void addListen(JButton o){
		this.Button_observers[this.Btn_o_i++] = o;
	}
	
	public void updateObserver(){
		int i;
		for(i=0;i<this.Menu_o_i;i++){
			this.MenuItem_observers[i].addActionListener(this);
		}
		for(i=0;i<this.Btn_o_i;i++){
			this.Button_observers[i].addActionListener(this);
		}
	}
	
	
	
}
