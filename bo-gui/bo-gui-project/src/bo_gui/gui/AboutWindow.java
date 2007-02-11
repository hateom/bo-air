package bo_gui.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 710618804523797333L;

	AboutWindow ( ){
		super("About...");
		setSize( 350, 250 );
		setLayout( new BorderLayout() );
		JPanelExtended panel = new JPanelExtended();
		panel.setFillType(GridBagConstraints.NONE);
		this.add( panel, BorderLayout.CENTER );
		JLabel label = new JLabel("BO Processor Frontend");
		panel.doloz( label );
		panel.lf();
		label = new JLabel("Tabu Search Method Solver");
		panel.doloz( label );
		panel.lf();
		label = new JLabel("Project Authors: Tomasz Huczek, Andrzej Jasinski");
		panel.doloz( label );
		panel.lf();
		setVisible( true );
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
