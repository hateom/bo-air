package bo_gui.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class Area_MapArea extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -180033844694872349L;
	private MainWindow okno;

	public Area_MapArea(MainWindow okno){ 
		this.okno = okno;
		setBackground(Color.WHITE);
		setOpaque(true);
	}
	
	protected void paintComponent(Graphics g1){
		Graphics2D g = (Graphics2D)g1;
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		super.paintComponent(g);
	}
}
