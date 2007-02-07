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
	private List<Float> points;
	
	private BufferedImage image;
	
	public Area_GraphArea(MainWindow okno){ 
		this.okno = okno;
		setBackground(Color.WHITE);
		setOpaque(true);
		image = new BufferedImage(MainWindow.Graphs_WIDTH,MainWindow.Graphs_HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	
	public void clear(){
		Graphics2D g = image.createGraphics();
		g.setBackground(Color.WHITE);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void setPoints( List<Float> points){
		if (points != null && points.size() != 0){
			this.points = points;
			clear();
			drawGrid();
			drawGraph();
			//Dimension dims = this.getSize();

		}else
		{
			
		}
	}
	
	public void drawGrid(){
		Graphics2D g = image.createGraphics();
		g.setColor( Color.lightGray );
		for (int i=0;i<MainWindow.Graphs_WIDTH;i+=50){
			g.drawLine(i, 0, i, MainWindow.Graphs_HEIGHT);
		}
		for (int u=0;u<MainWindow.Graphs_HEIGHT;u+=50){
			g.drawLine(0, u, MainWindow.Graphs_WIDTH, u);
		}
	}
	
	public void drawGraph(){
		int h = getHeight(); 
		int list_size = points.size();
		int minimum_y= Integer.MAX_VALUE;
		
		Graphics2D g = image.createGraphics();
		g.setColor(Color.RED);
		
		for (int i=0;i<list_size-1;i++){
			if ( minimum_y > (int)(Math.abs(points.get(i))) ) minimum_y = (int)( Math.abs(points.get(i)) );
		}
		
		//System.out.println(minimum_y);
		//System.out.println( points.get(okno.menager.getMaxVal()) );
		float yratio = (getHeight()-40)/( points.get(okno.menager.getMaxVal()) - minimum_y );
		float xratio = getWidth()/points.size();
		//float yratio2 = getHeight()/( points.get(okno.menager.getMaxVal()));
		for (int i=0;i<list_size-1;i++){
			g.drawLine((int)(xratio*i),h-20-(int)(yratio*(points.get(i)-minimum_y)),(int)(xratio*(i+1)), h-20-(int)(yratio*(points.get(i+1)-minimum_y)));
			//g.drawLine((int)(xratio*i),h-(int)(yratio2*(points.get(i))),(int)(xratio*(i+1)), h-(int)(yratio2*(points.get(i+1))));
		}
		this.repaint();
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
