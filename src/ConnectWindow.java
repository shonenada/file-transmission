import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class ConnectWindow extends DFrame{

	String host;
	String name;
	JLabel hostLabel;
	JTextField hostField;
	JButton confirmBtn;
	JButton cancelBtn;
	Window target;


	ConnectWindow(Window target){
		super("Connect",300,150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.target = target;
	}

	@Override
	void InitLayout(){
		setLayout(null);

		this.hostLabel = new JLabel("Server IP: ");
		this.hostField = new JTextField("localhost");
		this.confirmBtn = new JButton("Connect");
		this.cancelBtn = new JButton("Cancel");

		this.hostLabel.setBounds(10, 20, 100, 30);
		this.hostField.setBounds(100, 20, 150, 30);
		this.confirmBtn.setBounds(20, 70, 100, 30);
		this.cancelBtn.setBounds(150, 70, 100, 30);

		this.confirmBtn.addActionListener( new ConfirmBtnHandler(this) );
		this.cancelBtn.addActionListener( new CancelBtnHandler(this) );

		getRootPane().setDefaultButton(confirmBtn);

		this.add(this.hostLabel);
		this.add(this.hostField);
		this.add(this.confirmBtn);
		this.add(this.cancelBtn);
	}

	void ConnetServer(){
		this.host = this.hostField.getText();
		if ( this.host.equals("") || this.host == null){
			target.AppendInfo("Please Input the Server IP.");
		}else{
			this.target.ConnectToServer( this.host );
		}
	}
}

class ConfirmBtnHandler implements ActionListener{

	private ConnectWindow target;

	ConfirmBtnHandler(ConnectWindow target){
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		this.target.ConnetServer();
		this.target.dispose();
	}
}

class CancelBtnHandler implements ActionListener{

	private ConnectWindow target;

	CancelBtnHandler(ConnectWindow target){
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		this.target.dispose();
	}
}