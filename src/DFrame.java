import java.awt.*;
import javax.swing.*;

import java.awt.Toolkit;

public abstract class DFrame extends JFrame{

	/**
	 * 窗口尺寸参数
	 * WIN_HEIGHT	integer	窗口高度
	 * WIN_WIDTH	integer	窗口宽度
	 */
	protected int WIN_HEIGHT = 0;
	protected int WIN_WIDTH = 0;
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	DFrame(String title, int width, int height){
		setTitle(title);
		setWinWidth(width);
		setWinHeight(height);
		flushSize();
		setMiddleLocation();
		showWin();
		InitLayout();
		validate();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	

	
	int getWinWidth(){
		return this.WIN_WIDTH;
	}
	
	int getWinHeight(){
		return this.WIN_HEIGHT;
	}
	
	void setWinWidth(int width){
		this.WIN_WIDTH = width;
		flushSize();
	}
	
	void setWinHeight(int height){
		this.WIN_HEIGHT = height;
		flushSize();
	}
	
	void flushSize(){
		setSize(this.WIN_WIDTH, this.WIN_HEIGHT);
	}
	
	void setMiddleLocation(){
		setLocation(getMidHorizontal(this.WIN_WIDTH), getMidVertical(this.WIN_HEIGHT));
	}
	
	void showWin(){
		setVisible(true);
	}
	
	void hideWin(){
		setVisible(false);
	}
	
	void closeWin(){
		this.hideWin();
		this.dispose();
	}
		
	abstract void InitLayout();
	
	static int getMidHorizontal(int width){
		return (screenSize.width-width)/2;
	}

	static int getMidVertical(int height){
		return (screenSize.height-height)/2;
	}
	
	static void print(String str){
		System.out.println(str);
	}
	static void print(int s){
		System.out.println(s);
	}

}
