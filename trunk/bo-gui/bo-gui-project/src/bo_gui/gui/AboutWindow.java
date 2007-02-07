package bo_gui.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 710618804523797333L;

	AboutWindow ( ){
		super("Configure parameters...");
		setSize( 600, 500 );
		JLabel label = new JLabel("Greetz Lolz!! \n Power of Java && C++");
		add(label);
		setVisible( true );
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
