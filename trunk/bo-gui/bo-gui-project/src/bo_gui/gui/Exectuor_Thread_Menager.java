package bo_gui.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import bo_gui.executor.ProcessorExecutor;
import bo_gui.executor.SolutionStruct;

public class Exectuor_Thread_Menager {
	
	private Thread exec = null;
	private MainWindow okno;
	private List<Float> results_list;
	private int best_solution;
	private boolean halted = false;
	private Process proces;
	private String filename, prog_name, params;
	private int k_val;
	
	public Exectuor_Thread_Menager( MainWindow okno){
		this.okno = okno;
		results_list = new ArrayList<Float>();
		if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
			prog_name = "processor.exe";
		} else{
			prog_name = "./processor";
		}
		k_val = 45;
	}
	
	
	public synchronized void start_thread(){
		params = "--K="+k_val;
/*
K - iteracje
T - żywotność elementu na liście TABU

**/
		boolean error = !(new File(prog_name).exists());
			if (error){
				print_debug_info("[M]No executor file, opening dialog");
				File file = okno.ShowOpenFilePopup("Open Executor File...");
					if (file != null ) {
						prog_name = file.getAbsolutePath();
						error = !file.exists();
					}
			}
		if( filename != "" && filename != null && !error){
			if (exec!=null && exec.isAlive()){
				terminate_thread();
				proces.destroy();
				halted = true;
				okno.StartButton.setEnabled(false);
			}
			else{
				halted = false;
				Calendar datownik = Calendar.getInstance();
				print_debug_info("-----" + datownik.getTime().toString()+"-----");
				print_debug_info("[M]Starting processing thread...");
				exec = new Thread( new ProcessorExecutor(this, prog_name, params, filename) );
				exec.start();
				okno.StartButton.setText("Stop");
			}
		} else if (error){
			print_debug_info("[M]No executor file...");
		} 
		else{
			print_debug_info("[M]No input file..");
		}
	}
	public synchronized void setFileName( String filename){
		this.filename = filename;
	}
	public synchronized void signalizeFinish(){
		okno.StartButton.setEnabled(true);
		okno.StartButton.setText("Start");
	}
	
	public synchronized void setProcess( Process proc){
		proces = proc;
	}
	
	public synchronized void terminate_thread(){
		print_debug_info("[M]Requesting process termination...");
		
		exec.interrupt();
	}
	
	public synchronized void setProcessResult( List<SolutionStruct> solution ){
		if (halted){
			results_list.clear();
		}else{
			results_list.clear();
			float max_val = 0.0f;
			
			for (int i=0; i<solution.size();i++ ){
				results_list.add(solution.get(i).profit);
				if (solution.get(i).profit > max_val) {
					best_solution = i;
					max_val = solution.get(i).profit;
				}
			}
			okno.graph.setPoints(results_list);
			if ( results_list.size() != 0 ) okno.map.DrawSolution(solution.get(best_solution));
		}
		okno.StartButton.setEnabled(true);
	}
	
	public int getMaxVal(){
		return best_solution;
	}
	
	public void setKval( int value ){
		k_val = value;
	}
	// taka struktura zeby uniknac bledow  Interrupted attempt to aquire write lock..
	public void print_debug_info( final String text ) {
	        Runnable setPaneText = new Runnable() {
	            public void run() {
	            	okno.text_pane.setText(okno.text_pane.getText()+text+"\n");
	        		okno.text_pane.setCaretPosition(okno.text_pane.getDocument().getLength());
	            }
	        };
	        SwingUtilities.invokeLater(setPaneText);
	    }
}
