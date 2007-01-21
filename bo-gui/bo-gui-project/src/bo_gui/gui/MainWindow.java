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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;

import bo_gui.executor.ProcessorExecutor;
import bo_gui.executor.SolutionStruct;

public class MainWindow{
	
	private JScrollPane scroll_pane;
	
	protected JTextPane text_pane;
	protected Exectuor_Thread_Menager menager;
	protected Area_MapArea map;
	protected Area_GraphArea graph;
	protected JButton StartButton,OpenFileButton;
	protected JFileChooser fc;
	
	public MainWindow(){
		
		menager = new Exectuor_Thread_Menager(this);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
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
		map.setPreferredSize(new Dimension(500,300));
		map.setMinimumSize(new Dimension(500,300));
		map.setSize(new Dimension(500,300));
		map.setToolTipText("Mapka");
		left_panel.doloz(map);
		
		left_panel.lf();
		
		graph = new Area_GraphArea(this);
		graph.setPreferredSize(new Dimension(500,300));
		graph.setMinimumSize(new Dimension(500,300));
		graph.setSize(new Dimension(500,300));
		graph.setToolTipText("Wykres funkcji celu");
		left_panel.doloz(graph);
		graph.clear();
		
		frame.getContentPane().add(main_panel,BorderLayout.WEST);
		frame.pack();
		//frame.setSize(900,600);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	public class JButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == StartButton){
				menager.start_thread();
			}
			if (event.getSource() == OpenFileButton){
				fc.setDialogTitle("Open Map File...");
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				 int returnVal = fc.showOpenDialog(null);
				 if (returnVal == JFileChooser.APPROVE_OPTION) {
					 File file = fc.getSelectedFile();
					 menager.print_debug_info("[G]Opening file.."+file.getName());
					 menager.setFileName(file.getAbsolutePath());
					 map.DrawMap(file);
				 }
				 else {
					 
				 }
			}
		}
		
	}
/*	
	public class FileSelectorListener implements ActionListener{
		public FileSelectorListener(){
			
		}
		public void actionPerformed(ActionEvent arg0) {
			
		}
		
	}
	*/
}
