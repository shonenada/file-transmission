import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

class AboutWindow extends DFrame{
	
	JLabel titleLabel;
	JLabel authorLabel;
	JLabel inforLabel;
	JButton closeBtn;
	
	AboutWindow(){
		super("About ",300,200);
		InitLayout();
		validate();
	}
	
	void InitLayout(){
		setLayout(null);
		
		titleLabel = new JLabel("<html><font size=+2>About</font></html>", JLabel.CENTER);
		authorLabel = new JLabel("Author: Lyd.", JLabel.LEFT);
		inforLabel = new JLabel("<html><p>Copyright by Lyd</p></html>", JLabel.LEFT);
		closeBtn = new JButton("CLOSE");
		
		add(titleLabel);
		add(authorLabel);
		add(inforLabel);
		add(closeBtn);
		
		titleLabel.setBounds(90,5,100,40);
		titleLabel.setVerticalAlignment(JLabel.TOP);
		
		authorLabel.setBounds(5,50,300,25);
		authorLabel.setVerticalAlignment(JLabel.TOP);
		
		inforLabel.setBounds(5,85,300,25);
		inforLabel.setVerticalAlignment(JLabel.TOP);
		
		closeBtn.setBounds(100,120,80,30);
		closeBtn.setVerticalAlignment(JButton.TOP);
		closeBtn.addActionListener( new closeBtnHandler(this) );
		
	}
}

class closeBtnHandler implements ActionListener{

	AboutWindow target;

	closeBtnHandler(AboutWindow target){
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		this.target.dispose();
	}
}