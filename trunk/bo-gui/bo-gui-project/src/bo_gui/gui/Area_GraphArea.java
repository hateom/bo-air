package bo_gui.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JComponent;

public class Area_GraphArea extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5153369237812057727L;
	
	private MainWindow okno;
	private List<Integer> points;
	private BufferedImage image;
	
	public Area_GraphArea(MainWindow okno){ 
		this.okno = okno;
		setBackground(Color.WHITE);
		setOpaque(true);
		image = new BufferedImage(500,300, BufferedImage.TYPE_INT_RGB);
	}
	
	
	public void clear(){
		Graphics2D g = image.createGraphics();
		g.setBackground(Color.WHITE);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void setPoints( List<Float> points){
		if (points != null){
			
			Dimension dims = this.getSize();
			int h = getHeight(); 
			int list_size = points.size();
			clear();
			Graphics2D g = image.createGraphics();
			g.setColor(Color.RED);
			float yratio = getHeight()/points.get(okno.menager.getMaxVal());
			float xratio = getWidth()/points.size();
			for (int i=0;i<list_size-1;i++){
				g.drawLine((int)(xratio*i),h-(int)(yratio*points.get(i)),(int)(xratio*(i+1)), h-(int)(yratio*points.get(i+1)));
			}
			this.repaint();
		}else
		{
			
		}
	}

	protected void paintComponent(Graphics g1){
		Graphics2D g = (Graphics2D)g1;
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(image,null,null);
		}
		super.paintComponent(g);
	}

}
