package bo_project;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Bo_gui {
	public Bo_gui(Container pojemnik){
		JPanel panel = new JPanel();
		List command = new ArrayList();
	/*
		command.add("cmd.exe");
		command.add("/c");
		command.add("start");
		command.add("processor.exe");
		command.add("-s");
		command.add("input_data");
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.directory( new File ("C:\\projekty\\bo-air\\bo-gui\\bin\\bo_project"));
		final Process process = builder.start();
	*/	
		panel.setLayout(new BorderLayout());
		JLabel label = new JLabel("Maciora");
		panel.add(label);
		pojemnik.add(panel,BorderLayout.CENTER);
		String [] cmd = {"cmd.exe"}; 
		Process process = Runtime.getRuntime().exec(cmd);
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	}
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGUI();
			}
		});
	}
	
	private static void createGUI(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("BO : Tabu Search Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		Bo_gui okno = new Bo_gui(frame.getContentPane());
		frame.pack();
		frame.setSize(900,600);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	private 
		JLabel label;
}
