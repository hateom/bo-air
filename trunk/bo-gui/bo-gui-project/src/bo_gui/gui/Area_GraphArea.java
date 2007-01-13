package bo_gui.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class Area_GraphArea extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5153369237812057727L;
	
	private MainWindow okno;
	
	public Area_GraphArea(MainWindow okno){ 
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
