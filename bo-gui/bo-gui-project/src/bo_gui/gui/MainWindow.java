package bo_gui.gui;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import bo_gui.executor.ProcessorExecutor;

public class MainWindow {
	public MainWindow(){
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("BO : Tabu Search Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		//JLabel label = new JLabel("Maciora");
		JTextPane pane = new JTextPane();
		panel.add(pane);
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		frame.pack();
		frame.setSize(900,600);
		frame.setResizable(false);
		frame.setVisible(true);
		
		
		ProcessorExecutor exec = new ProcessorExecutor();
		try {
			exec.Execute("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float zysk = exec.getZysk();
		String linia = exec.getOutput();
		int[] wektorAnten = exec.getWektorAnten();
		
		pane.setText("" + linia);
	}
}
