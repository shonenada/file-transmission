import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectWindow extends DFrame{
	
	String host;
	JTextField host_field;
	JLabel host_label;
	JButton confirm_btn;
	JButton cancel_btn;

	ConnectWindow(Window target){
		super("Connect",300,150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	//	this.target = target;
		InitLayout();
	}

	void InitLayout(){
		setLayout(null);
		host_label = new JLabel("Server IP: ");
		host_field = new JTextField();
		confirm_btn = new JButton("Connect");
		cancel_btn = new JButton("Cancel");
		host_label.setBounds(10, 20, 80, 30);
		host_field.setBounds(100, 20, 150, 30);
		confirm_btn.setBounds(20, 70, 100, 30);
		cancel_btn.setBounds(150, 70, 100, 30);

		confirm_btn.addActionListener( new ConfirmBtnHandler() );
		cancel_btn.addActionListener( new CancelBtnHandler(this) );

		this.add(host_label);
		this.add(host_field);
		this.add(confirm_btn);
		this.add(cancel_btn);
	}

}

class ConfirmBtnHandler implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){
		System.out.println("test");
	}
}

class CancelBtnHandler implements ActionListener{

	private ConnectWindow win;

	CancelBtnHandler(ConnectWindow x){
		this.win = x;
	}
	@Override
	public void actionPerformed(ActionEvent e){
		this.win.dispose();
	}
}