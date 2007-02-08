package bo_gui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import bo_gui.executor.ProcessorExecutor;
import bo_gui.executor.SolutionStruct;

public class MainWindow
			implements ChangeListener
{
	static final int K_MIN = 1;
	static final int K_MAX = 51;
	static final int K_INIT = 20; 
	
	static final int T_MIN = 1;
	static final int T_MAX = 15;
	static final int T_INIT = 5; 
	
	static final int A_MIN = 1;
	static final int A_MAX = 5;
	static final int A_INIT = 2; 
	private JScrollPane scroll_pane;
	
	private JSlider K_slider, T_slider, Alpha_slider;
	
	protected JTextPane text_pane;
	protected Exectuor_Thread_Menager menager;
	protected Area_MapArea map;
	protected Area_GraphArea graph;
	protected JButton StartButton,OpenFileButton;
	protected JFileChooser fc;
	protected JButton configurationButton, aboutBtn;
	protected ConfigurationWindow confWnd;
	protected AboutWindow aboutWnd;
	
	public static final int Graphs_WIDTH = 500;
	public static final int Graphs_HEIGHT = 300;
	public MainWindow(){
		
		menager = new Exectuor_Thread_Menager(this);
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
		JFrame frame = new JFrame("BO : Tabu Search Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		right_panel.lf();
		
		JLabel slider_label = new JLabel("Ilosc iteracji (k)");
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
		
		slider_label = new JLabel("T whatever");
		right_panel.doloz(slider_label);
		T_slider = new JSlider(JSlider.HORIZONTAL, T_MIN, T_MAX, T_INIT);
		T_slider.setMinorTickSpacing(1);
		T_slider.setPaintTicks(true);
		T_slider.setPaintLabels(true);
		T_slider.setMajorTickSpacing(5);
		T_slider.addChangeListener(this);
		right_panel.doloz(T_slider);
		
		right_panel.lf();
		
		slider_label = new JLabel("Alpha param");
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
		
		frame.getContentPane().add(main_panel,BorderLayout.WEST);
		frame.pack();
		//frame.setSize(900,600);
		frame.setResizable(false);
		frame.setVisible(true);
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
	
	public class JButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == StartButton){
				menager.start_thread();
			}
			if (event.getSource() == OpenFileButton){
				File file = ShowOpenFilePopup("Open Map File");
				if (file!= null){
					menager.print_debug_info("[G]Opening file.."+file.getName());
					menager.setFileName(file.getAbsolutePath(), file);
					map.DrawMap(file);
				 }
			}
			
			if (event.getSource() == configurationButton){
				if ( confWnd == null ) confWnd = new ConfigurationWindow(menager);
				else confWnd.setVisible(true);
			}
			
			if (event.getSource() == aboutBtn){
				if ( aboutWnd == null ) aboutWnd = new AboutWindow();
				else aboutWnd.setVisible(true);
			}
			else {
				
			}
		}
		
	}
}
