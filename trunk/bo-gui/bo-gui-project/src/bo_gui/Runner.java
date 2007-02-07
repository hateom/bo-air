package bo_gui;

import bo_gui.gui.MainWindow;

public class Runner {
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
					new MainWindow();
			}
		});
	}	
}
