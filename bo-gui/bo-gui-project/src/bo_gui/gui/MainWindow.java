package bo_gui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.sun.image.codec.jpeg.*;

public class MainWindow
			extends JFrame 
			implements ChangeListener,
						ActionListener,
						ItemListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7573712310630205875L;
	static final int K_MIN = 1;
	static final int K_MAX = 51;
	static final int K_INIT = 20; 
	
	static final int T_MIN = 1;
	static final int T_MAX = 15;
	static final int T_INIT = 5; 
	
	static final int A_MIN = 1;
	static final int A_MAX = 20;
	static final int A_INIT = 2; 
	private JScrollPane scroll_pane;
	
	private JSlider K_slider, T_slider, Alpha_slider;
	
	protected JTextPane text_pane;
	protected Executor_Thread_Menager menager;
	protected Area_MapArea map;
	protected Area_GraphArea graph;
	protected JButton StartButton,OpenFileButton, batchBtn;
	protected JFileChooser fc;
	protected JButton configurationButton, aboutBtn;
	protected ConfigurationWindow confWnd;
	protected AboutWindow aboutWnd;
	protected Area_LegendArea legend;
	protected JLabel best_sol;
	protected JCheckBox saveOutputToggle;
	private String path = System.getProperty("user.dir");
	private boolean saveBool = false;
	//protected BatchWindow batchWnd;
	
	
	public static final int Graphs_WIDTH = 500;
	public static final int Graphs_HEIGHT = 300;
	public static final int LEGEND_WIDTH  = 200;
	public static final int LEGEND_HEIGHT = 100;
	public MainWindow(){
		super("BO : Tabu Search Project");
		menager = new Executor_Thread_Menager(this);
		confWnd = null;
		aboutWnd = null;
		JFrame.setDefaultLookAndFeelDecorated(false);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//JFrame frame = new JFrame("BO : Tabu Search Project");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		fc = new JFileChooser();
		
		JPanel main_panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		/*
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		*/
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		JPanelExtended left_panel = new JPanelExtended();
		main_panel.add(left_panel);
		
		c.gridx = 1;
		c.gridy = 0;
		JPanelExtended right_panel = new JPanelExtended();
		main_panel.add(right_panel);
		
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		JPanel bottom_panel = new JPanel();
		main_panel.add(bottom_panel);
		JButtonListener ActionListener = new JButtonListener();
		
		
		JLabel legend_label =  new JLabel ("Legend");
		right_panel.doloz(legend_label);
		right_panel.lf();
		
		legend = new Area_LegendArea(this);
		
		legend.setPreferredSize( new Dimension(200,100) );
		legend.setMinimumSize( new Dimension(200,100) );
		legend.setSize( new Dimension(200,100) );
		
		right_panel.doloz( legend, true );
		
		OpenFileButton = new JButton("Open Map...");
		right_panel.doloz(OpenFileButton);
		OpenFileButton.addActionListener(ActionListener);
		
		StartButton = new JButton("Start!");
		right_panel.doloz(StartButton);
		StartButton.addActionListener(ActionListener);
		
		right_panel.lf();
		
		configurationButton = new JButton("Configuration");
		configurationButton.addActionListener(ActionListener);
		right_panel.doloz(configurationButton);
		
		right_panel.lf();
		
		aboutBtn = new JButton("About...");
		aboutBtn.addActionListener(ActionListener);
		right_panel.doloz(aboutBtn);
		/*
		batchBtn = new JButton("Batch Mode");
		batchBtn.addActionListener(this);
		right_panel.doloz(batchBtn);
		*/
		
		saveOutputToggle = new JCheckBox("Save Output to files");
		saveOutputToggle.addItemListener(this);
		saveOutputToggle.setSelected(false);
		right_panel.doloz( saveOutputToggle );
		
		right_panel.lf();
		
		JLabel slider_label = new JLabel("Iteration number (K)");
		right_panel.doloz(slider_label);
		
		K_slider = new JSlider(JSlider.HORIZONTAL, K_MIN, K_MAX, K_INIT);
		//K_slider.setMajorTickSpacing(10);
        K_slider.setMinorTickSpacing(5);
		K_slider.setPaintTicks(true);
		K_slider.setPaintLabels(true);
		
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put( new Integer( 1 ), new JLabel("1") );
		labelTable.put( new Integer( 10 ), new JLabel("10") );
		labelTable.put( new Integer( 20 ), new JLabel("20") );
		labelTable.put( new Integer( 30 ), new JLabel("30") );
		labelTable.put( new Integer( 40 ), new JLabel("40") );
		labelTable.put( new Integer( 50 ), new JLabel("50") );
		K_slider.setLabelTable( labelTable );
		K_slider.addChangeListener(this);
		right_panel.doloz(K_slider);
		
		right_panel.lf();
		
		slider_label = new JLabel("T parameter");
		right_panel.doloz(slider_label);
		T_slider = new JSlider(JSlider.HORIZONTAL, T_MIN, T_MAX, T_INIT);
		T_slider.setMinorTickSpacing(1);
		T_slider.setPaintTicks(true);
		T_slider.setPaintLabels(true);
		T_slider.setMajorTickSpacing(5);
		T_slider.addChangeListener(this);
		right_panel.doloz(T_slider);
		
		right_panel.lf();
		
		slider_label = new JLabel("Alpha parameter");
		right_panel.doloz(slider_label);
		Alpha_slider = new JSlider(JSlider.HORIZONTAL, A_MIN, A_MAX, A_INIT);
		Alpha_slider.setMinorTickSpacing(1);
		Alpha_slider.setMajorTickSpacing(5);
		Alpha_slider.setPaintTicks(true);
		Alpha_slider.setPaintLabels(true);
		Alpha_slider.addChangeListener(this);
		right_panel.doloz(Alpha_slider);
		
		right_panel.lf();
		
		JLabel text_pane_label = new JLabel("Console");
		right_panel.doloz(text_pane_label);
		
		text_pane = new JTextPane();
		scroll_pane = new JScrollPane(text_pane);
		scroll_pane.setPreferredSize(new Dimension(400,100));
		scroll_pane.setMinimumSize(new Dimension(400,100));
		scroll_pane.setSize(400, 100);
		//left_panel.setMinimumSize(new Dimension(500,500));
		text_pane.setCaretPosition(text_pane.getDocument().getLength());

		right_panel.lf();
		right_panel.doloz(scroll_pane, true);
		
		JLabel lab = new JLabel( "Best solution: " );
		right_panel.doloz(lab);
		best_sol =  new JLabel("");
		right_panel.doloz(best_sol);
		
		
		map = new Area_MapArea(this);
		map.setPreferredSize(new Dimension(Graphs_WIDTH, Graphs_HEIGHT));
		map.setMinimumSize(new Dimension(Graphs_WIDTH, Graphs_HEIGHT));
		map.setSize(new Dimension(Graphs_WIDTH, Graphs_HEIGHT));
		map.setToolTipText("Mapka");
		left_panel.doloz(map);
		
		left_panel.lf();
		
		graph = new Area_GraphArea(this);
		graph.setPreferredSize(new Dimension(Graphs_WIDTH, Graphs_HEIGHT));
		graph.setMinimumSize(new Dimension(Graphs_WIDTH, Graphs_HEIGHT));
		graph.setSize(new Dimension(Graphs_WIDTH, Graphs_HEIGHT));
		graph.setToolTipText("Wykres funkcji celu");
		left_panel.doloz(graph);
		graph.clear();
		
		this.getContentPane().add(main_panel,BorderLayout.WEST);
		this.pack();
		//frame.setSize(900,600);
		this.setResizable(false);
		this.setVisible(true);
		menager.print_debug_info("Current OS is : "+System.getProperty("os.name"));
	}
	
	public File ShowOpenFilePopup(String title){
		File file= null;
		fc.setDialogTitle(title);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		} else {
			
		}
		return file;
	}
	
	public void stateChanged(ChangeEvent e) {
		/*
        JSlider source = (JSlider)e.getSource();
        menager.setKval(source.getValue());*/
		JSlider source = (JSlider)e.getSource();
		if ( e.getSource() == K_slider ){
			menager.setKval(source.getValue());
			Alpha_slider.setMaximum(source.getValue());
		} else if (e.getSource() == T_slider ){
			menager.setTval(source.getValue());
		} else{
			menager.setAplhaVal(source.getValue());
		}
		
    }
	
	public void setBestSolLabel( String label ){
		best_sol.setText(label);
	}
	
	public class JButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == StartButton){
				menager.start_thread();
			}
			
			else if (event.getSource() == OpenFileButton){
				File file = ShowOpenFilePopup("Open Map File");
				if (file!= null){
					menager.print_debug_info("[G]Opening file.."+file.getName());
					menager.setFileName(file.getAbsolutePath(), file);
					map.DrawMap(file);
					legend.DrawLegend();
				 }
			}
			
			if (event.getSource() == configurationButton){
				if ( confWnd == null ) confWnd = new ConfigurationWindow(menager);
				else {
					confWnd.setVisible(true);
					confWnd.set_sliders(menager.getOptTable());
				}
			}
			
			if (event.getSource() == aboutBtn){
				if ( aboutWnd == null ) aboutWnd = new AboutWindow();
				else aboutWnd.setVisible(true);
			}
			else {
				
			}
		}
		
	}
	
	public void jpgWriter( String Filename, BufferedImage image) throws Exception{
		if(Filename != null){
			FileOutputStream fo = new FileOutputStream(Filename);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fo);
			encoder.encode(image);
			//encoder.
			fo.close();
		}
	}

	public void saveToImage( String inputName ){
		BufferedImage mapImage = map.getImage();
		BufferedImage graphImage = graph.getImage();
		BufferedImage legendImage = legend.getImage();
		if ( saveBool ){
			try{
				String Filename = path+"/"+inputName+"_config"+".txt";
				if(Filename != null){
					FileWriter writer = new FileWriter( Filename );
					writer.write("K = "+ menager.getK_val() +"\n");
					writer.write("T = "+ menager.getT_val() +"\n");
					writer.write("Alpha = " + menager.getAlphaVal() +"\n");
					Hashtable <String, List<Float>> table = menager.getOptTable(); 
					for (Iterator iter = table.keySet().iterator(); iter.hasNext();) {
						String key = (String) iter.next();
						List<Float> list = table.get(key);
						writer.write(key+"\n");
						for(int i=0;i<list.size();i++){
							writer.write(list.get(i)+" ");
						}
						writer.write(";\n");
					}
					writer.write( "Best Solution (Profit): "+menager.getMaxVal());
					writer.flush( );
					writer.close( );


				}

				Filename = path+"/"+inputName+"_map"+".jpg";
				jpgWriter(Filename, mapImage);
				Filename = path+"/"+inputName+"_graph"+".jpg";
				jpgWriter(Filename, graphImage);
				Filename = path+"/"+inputName+"_legend"+".jpg";
				jpgWriter(Filename, legendImage);
				
			}
			catch(Exception ex){
				ex.printStackTrace();
				menager.print_debug_info("[!!] Writing image failure!");
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == batchBtn){
			//batchWnd = new BatchWindow( this );
		}
	}

	public void itemStateChanged(ItemEvent event) {
		if ( event.getSource()== saveOutputToggle ){
			if ( event.getStateChange() == ItemEvent.SELECTED ){
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setCurrentDirectory( new File ( path ) );
				File file = ShowOpenFilePopup("Choose Output Directory");
				if (file != null && file.getAbsolutePath() != "") {
					path = file.getAbsolutePath();
					saveBool = true;
				} else {
					saveOutputToggle.setSelected( false );
					saveBool = false;
				}
				
			} else 
			{ 
				saveBool = false;
			}
		}
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	public Area_GraphArea getGraph() {
		return graph;
	}

	public void setGraph(Area_GraphArea graph) {
		this.graph = graph;
	}

	public Area_MapArea getMap() {
		return map;
	}

	public void setMap(Area_MapArea map) {
		this.map = map;
	}
}
