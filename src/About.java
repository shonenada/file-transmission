import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

class About extends DFrame implements ActionListener{
	
	JLabel Label_title;
	JLabel Label_author;
	JLabel Label_infor;
	JButton Btn_close;
	
	About(){
		super("关于",300,200);
		InitLayout();
		validate();
	}
	
	void InitLayout(){
		setLayout(null);
		
		Label_title = new JLabel("<html><font size=+2>About</font></html>", JLabel.CENTER);
		Label_author = new JLabel("Author: Lyd.", JLabel.LEFT);
		Label_infor = new JLabel("<html><p>Copyright by Lyd</p></html>", JLabel.LEFT);
		Btn_close = new JButton("CLOSE");
		
		add(Label_title);
		add(Label_author);
		add(Label_infor);
		add(Btn_close);
		
		Label_title.setBounds(90,5,100,40);
		Label_title.setVerticalAlignment(JLabel.TOP);
		
		Label_author.setBounds(5,50,300,25);
		Label_author.setVerticalAlignment(JLabel.TOP);
		
		Label_infor.setBounds(5,85,300,25);
		Label_infor.setVerticalAlignment(JLabel.TOP);
		
		Btn_close.setBounds(100,120,80,30);
		Btn_close.setVerticalAlignment(JButton.TOP);
		Btn_close.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.hideWin();
		this.dispose();
	}
	
}