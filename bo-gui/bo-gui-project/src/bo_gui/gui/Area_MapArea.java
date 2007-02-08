package bo_gui.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
		Map_creator = new MapImageBuilder( MainWindow.Graphs_WIDTH, MainWindow.Graphs_HEIGHT, okno.menager );//this.getWidth(), this.getHeight() );
	}
	
	public void DrawMap( File InputFile ){
		try {
			image = Map_creator.CreateMapImage(InputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			okno.menager.print_debug_info("[G-MB]Input File not found...");
		} catch (IOException e) {
			e.printStackTrace();
			okno.menager.print_debug_info("[G-MB]IOException");
		}
		if (image != null){
			
		}
		else{
			
		}
		this.repaint();
	}
	
	public void DrawSolution( SolutionStruct solution){
		Map_creator.CreateSolutionImage(solution);
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
