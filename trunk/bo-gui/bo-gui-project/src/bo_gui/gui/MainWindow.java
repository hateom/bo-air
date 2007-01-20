package bo_gui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;

import bo_gui.executor.ProcessorExecutor;
import bo_gui.executor.SolutionStruct;

public class MainWindow {
	
	protected JTextPane text_pane;
	private JScrollPane scroll_pane;
	protected Exectuor_Thread_Menager menager;
	
	public MainWindow(){
		
		menager = new Exectuor_Thread_Menager(this);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("BO : Tabu Search Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel main_panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		
		JPanel top_panel = new JPanel();
		main_panel.add(top_panel);
		
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;
		JPanelExtended left_panel = new JPanelExtended();
		main_panel.add(left_panel);
		
		c.gridx = 1;
		c.gridy = 1;
		JPanelExtended right_panel = new JPanelExtended();
		main_panel.add(right_panel);
		
		
		JButton StartButton = new JButton("Start!");
		right_panel.doloz(StartButton);
		JButtonListener ActionListener = new JButtonListener();
		StartButton.addActionListener(ActionListener);
		
		text_pane = new JTextPane();
		scroll_pane = new JScrollPane(text_pane);
		scroll_pane.setPreferredSize(new Dimension(500,300));
		scroll_pane.setMinimumSize(new Dimension(500,300));
		scroll_pane.setSize(500, 300);
		//left_panel.setMinimumSize(new Dimension(500,500));
		text_pane.setCaretPosition(text_pane.getDocument().getLength());
		left_panel.doloz(scroll_pane);
		
		
		left_panel.lf();
		
		Area_MapArea map = new Area_MapArea(this);
		map.setPreferredSize(new Dimension(500,300));
		map.setMinimumSize(new Dimension(500,300));
		map.setSize(new Dimension(500,300));
		map.setToolTipText("Rysowanie");
		left_panel.doloz(map);
		
		frame.getContentPane().add(main_panel,BorderLayout.CENTER);
		frame.pack();
		frame.setSize(900,600);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public class JButtonListener implements ActionListener{

		MainWindow okno;
		JButtonListener(){
		}
		
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub

			text_pane.setText("");
			menager.start_thread();
			//String linia = exec.getOutput();
		}
	}
}
