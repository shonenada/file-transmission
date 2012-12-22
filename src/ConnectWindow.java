import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConnectWindow extends DFrame{
	
	String host;
	JTextField hostField;
	JLabel hostLabel;
	JButton confirmBtn;
	JButton cancelBtn;
	Window target;

	ConnectWindow(Window target){
		super("Connect",300,150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.target = target;
		InitLayout();
	}

	void InitLayout(){
		setLayout(null);
		hostLabel = new JLabel("Server IP: ");
		hostField = new JTextField();
		confirmBtn = new JButton("Connect");
		cancelBtn = new JButton("Cancel");
		hostLabel.setBounds(10, 20, 80, 30);
		hostField.setBounds(100, 20, 150, 30);
		confirmBtn.setBounds(20, 70, 100, 30);
		cancelBtn.setBounds(150, 70, 100, 30);

		confirmBtn.addActionListener( new ConfirmBtnHandler(this) );
		cancelBtn.addActionListener( new CancelBtnHandler(this) );

		this.add(hostLabel);
		this.add(hostField);
		this.add(confirmBtn);
		this.add(cancelBtn);
	}

}

class ConfirmBtnHandler implements ActionListener{

	private ConnectWindow target;

	ConfirmBtnHandler(ConnectWindow target){
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		target.host = this.target.hostField.getText();

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