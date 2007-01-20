package bo_gui.gui;

import bo_gui.executor.ProcessorExecutor;

public class Exectuor_Thread_Menager {
	
	private Thread exec;
	private MainWindow okno;
	
	public Exectuor_Thread_Menager( MainWindow okno){
		this.okno = okno;
	}
	
	
	public void start_thread(){
		okno.text_pane.setText("jhuhu");
		exec = new Thread( new ProcessorExecutor(this) );
		exec.start();
	}
	
	public void terminate_thread(){
		
	}
	
	public void zrobcos( String text){
		okno.text_pane.setText(okno.text_pane.getText()+"\n"+text);
	}
}
