import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ConnectWindow extends DFrame{
	
	private String host;
	private JTextField host_field;
	private JLabel host_label;
	private JButton confirm_btn;
	private JButton cancel_btn;
	private Window target;

	private ActionListener confirmBtnListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("connect");
		}
	};

	private ActionListener cancelBtnListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("cancel");
		}
	};

	ConnectWindow(Window target){
		super("Connect",300,150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.target = target;
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

		this.add(host_label);
		this.add(host_field);
		this.add(confirm_btn);
		this.add(cancel_btn);

		confirm_btn.addActionListener(confirmBtnListener);
		cancel_btn.addActionListener(cancelBtnListener);

	}

}