package bo_gui.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bo_gui.executor.SolutionStruct;

public class MapImageBuilder {
	private List<String> linie_list;
	private int max_size;
	private int area_width, area_height,box_w,box_h;
	private BufferedImage image;
	private List<Integer> lista_pol;
	
	public MapImageBuilder(int w, int h){
		linie_list = new ArrayList<String>();
		lista_pol = new ArrayList<Integer>();
		area_height = h;
		area_width = w;
		image = new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);
	}
	public BufferedImage CreateMapImage( File InputFile ) throws IOException{
		String temp;
		if (InputFile!= null && InputFile.exists()){
			max_size = 0;
			linie_list.clear();
			lista_pol.clear();
			BufferedReader in
			   = new BufferedReader(new FileReader(InputFile.getAbsolutePath()));
			while( (temp = in.readLine()) != null){
				linie_list.add(temp);
				if ( temp.length() > max_size ) max_size = temp.length();  
			}
			clear();
			ParseLine();
		}
		
		return image;
	}
	
	public BufferedImage CreateSolutionImage ( SolutionStruct  solution ){
		Graphics2D g = image.createGraphics();
		int x,y,u;
		for(int i=0;i<solution.transmitter.size();i++){
			u = lista_pol.get(solution.transmitter.get(i).index);
			x = u % max_size;
			y = u / max_size; //max_size == map_width
			switch (solution.transmitter.get(i).type){
				case 0 : g.setColor(Color.RED); break;
				case 1 : g.setColor(Color.ORANGE); break;
				case 2 : g.setColor(Color.GREEN); break;
				case 3 : g.setColor(Color.PINK); break;
				default : g.setColor(Color.BLUE); break;
			}
			
			g.fillRect(x*box_w, y*box_h, box_w, box_h);
			//System.out.println(solution.transmitter.get(i).index + ":"+ solution.transmitter.get(i).type);
		}
		return image;
	}
	
	private void ParseLine(){
		if( !linie_list.isEmpty() ){
			int map_width=max_size, map_height = linie_list.size();
			box_w =(int)(area_width/map_width);
			box_h=(int)(area_height/map_height);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.BLACK);
			for(int u=0;u<map_height;u++){
				for(int i=0;i<map_width;i++){
					if(linie_list.get(u).charAt(i) == ' ') {
						g.setColor(Color.BLACK);
						g.drawRect(i*box_w, u*box_h, box_w, box_h);
						//g.fillRect(i*box_w, u*box_h, box_w, box_h);
						lista_pol.add(u*map_width+i);
					}
					else if(linie_list.get(u).charAt(i) == '+'){
						g.setColor(Color.GRAY);
						g.fillRect(i*box_w, u*box_h, box_w, box_h);
					}
				}
			}
		}
	}
	public void clear(){
		Graphics2D g = image.createGraphics();
		g.setBackground(Color.WHITE);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, area_width, area_height);
	}
}
