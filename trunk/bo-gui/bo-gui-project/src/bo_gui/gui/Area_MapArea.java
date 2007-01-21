package bo_gui.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JComponent;

import bo_gui.executor.SolutionStruct;

public class Area_MapArea extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -180033844694872349L;
	private MainWindow okno;
	private MapImageBuilder Map_creator;
	
	protected BufferedImage image, solution_image;

	public Area_MapArea(MainWindow okno){ 
		this.okno = okno;
		setBackground(Color.WHITE);
		setOpaque(true);
		Map_creator = new MapImageBuilder();
	}
	
	public void DrawMap( File InputFile ){
		image = Map_creator.CreateMapImage(InputFile);
		if (image != null){
			
		}
		else{
			
		}
	}
	
	public void DrawSolution( SolutionStruct solution){
		
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
