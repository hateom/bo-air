package bo_gui.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JComponent;

public class Area_GraphArea extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5153369237812057727L;
	
	public static final int YGRIDCNT = 10;
	public static final int XGRIDCNT = 14;
	public static final int OFFSET = 20;
	public static final int YSTART = 280;
	public static final int XSTART = 20;
	
	private MainWindow okno;
	private List<Float> points;
	
	private double xscale, yscale;
	
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
		
		for (int i=20;i<MainWindow.Graphs_WIDTH;i+=33){
			g.drawLine(i, 0, i, MainWindow.Graphs_HEIGHT-20);
		}
		for (int u=28;u<=MainWindow.Graphs_HEIGHT-20;u+=28){
			g.drawLine(20, u, MainWindow.Graphs_WIDTH, u);
		}
	}
	
	private void calculateScale( float minYval, float maxYval, int Xcount ){
		float diff = (maxYval - minYval);
		int it = 1;
		if ( diff == 0.0f ) yscale = 1;
		else if ( diff <= 10.0f ) yscale = 1;
		else if ( diff > 10.0f ){
			while( diff >= 10*it ){
				it++;
			}
		}
		yscale = 280.0d/(double)(it*10);
		xscale = 462.0d/(double)(points.size()-1);
		System.out.println("Max:"+maxYval+" - "+(float)(it*10+minYval)+" "+minYval);
		Graphics2D g = image.createGraphics();
		g.setColor( Color.BLACK );
		//g.setStroke(new BasicStroke(2.0f));
		int k=YGRIDCNT -1 ;
		for (int i=28; k>=0 ;i+=28 ){
				//g.drawLine(i, 0, i, MainWindow.Graphs_HEIGHT-20);
			g.drawString(Integer.toString((int)minYval+it*k--),15, i);
		}
		
		k=0;
		for (int i=20; k<=XGRIDCNT ;i+=33 ){
			//g.drawLine(i, 0, i, MainWindow.Graphs_HEIGHT-20);
		g.drawString( Integer.toString( 1+ (int)((float)((points.size()-1)*k++)/(float)(XGRIDCNT))),i, YSTART + 15 );
		}
		g.drawString("Iteracja", 230, MainWindow.Graphs_HEIGHT-22);
		AffineTransform saveXform = g.getTransform();
		g.translate(10.0, 280.0);
		g.rotate(-Math.PI/2);
		g.drawString("Zysk",0,0);
		g.setTransform(saveXform);
		
	}
	
	public void drawGraph(){
		//int h = getHeight(); 
		int list_size = points.size();
		float minimum_y= Integer.MAX_VALUE;
		
		Graphics2D g = image.createGraphics();
		g.setColor(Color.RED);
		
		for (int i=0;i<list_size-1;i++){
			if ( minimum_y > (Math.abs(points.get(i))) ) minimum_y = ( Math.abs(points.get(i)) );
		}
		
		//System.out.println(minimum_y);
		//System.out.println( points.get(okno.menager.getMaxVal()) );
		//float yratio = (getHeight()-40)/( points.get(okno.menager.getMaxVal()) - minimum_y );
		//float xratio = getWidth()/points.size();
		//float yratio2 = getHeight()/( points.get(okno.menager.getMaxVal()));
		
		calculateScale( minimum_y,  points.get(okno.menager.getMaxValIndex()), points.size());
		
		for (int i=0;i<list_size-1;i++){
			g.drawLine(XSTART+(int)(xscale*i),YSTART-(int)(yscale*(points.get(i)-Math.abs(minimum_y))),XSTART+(int)(xscale*(i+1)), YSTART-(int)(yscale*(points.get(i+1)-Math.abs(minimum_y))));
			//System.out.println((int)xscale*i+" "+(int)(yscale*(points.get(i)-minimum_y)));
			//g.drawLine((int)(xscale*i),h-(int)(yscale*(points.get(i)-minimum_y)),(int)(xscale*(i+1)), h-(int)(yscale*(points.get(i+1)-minimum_y)));
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
	
	public BufferedImage getImage(){
		return image;
	}
	
}
