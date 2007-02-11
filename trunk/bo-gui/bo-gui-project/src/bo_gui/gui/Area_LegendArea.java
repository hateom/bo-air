package bo_gui.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JComponent;

public class Area_LegendArea extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1800123144694872349L;
	private MainWindow okno;
	
	protected BufferedImage image = null;

	public Area_LegendArea(MainWindow okno){ 
		this.okno = okno;
		setBackground(Color.WHITE);
		setOpaque(true);
		//image = new BufferedImage(MainWindow.LEGEND_WIDTH,MainWindow.LEGEND_HEIGHT, BufferedImage.TYPE_INT_RGB);
		//Map_creator = new MapImageBuilder( MainWindow.Graphs_WIDTH, MainWindow.Graphs_HEIGHT, okno.menager );//this.getWidth(), this.getHeight() );
	}
	
	public void DrawLegend(  ){
		if ( image == null ) image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		clear();
		int u = 0;
		List<Float> profits = okno.menager.getOptTable().get("profit");
		int boxw, boxh;
		boxw = getWidth() / 26;
		boxh = getHeight() / 2;
		float maxval=0.0f;
		for(int i=0;i<profits.size();i++) if ( profits.get(i) > maxval ) maxval = profits.get(i);
		Float ratio = 255.0f / maxval ;
		for( int i='a';i<='z';i++ ){
			Float val = (float)255.0 - (ratio*profits.get(u));
			g.setColor( new Color( val.intValue(), val.intValue(), val.intValue() ) );
			g.fillRect( 5+u*boxw, 0, boxw, boxh );
			g.setColor( Color.BLACK );
			g.drawString(""+(char)i, 5+u*boxw+boxw/2, boxh+25);
			u++;
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
	
	public void clear(){
		Graphics2D g = image.createGraphics();
		g.setBackground(Color.WHITE);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public BufferedImage getImage(){
		return image;
	}
}
