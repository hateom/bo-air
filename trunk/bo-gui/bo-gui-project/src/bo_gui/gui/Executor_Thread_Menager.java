package bo_gui.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import javax.swing.SwingUtilities;

import bo_gui.executor.ProcessorExecutor;
import bo_gui.executor.SolutionStruct;

public class Executor_Thread_Menager {
	
	private Thread exec = null;
	private MainWindow okno;
	private List<Float> results_list;
	private int best_solution;
	private boolean halted = false;
	private Process proces;
	private String filename, prog_name;
	private int k_val ,t_val=5, alphaVal=2;
	private Hashtable<String, List<Float>> optTable = null;
	private String OptionFileName = "";
	private File file = null;
	private List<String> params = null;
	
	public String getOptionFileName() {
		return OptionFileName.toString();
	}


	public void setOptionFileName(String optionFileName) {
		/*
		 * Copying value not reference to prevent changing of reference
		 */
		OptionFileName = optionFileName.toString();
	}


	public Executor_Thread_Menager( MainWindow okno){
		this.okno = okno;
		results_list = new ArrayList<Float>();
		if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
			prog_name = "processor.exe";
		} else{
			prog_name = "./processor";
		}
		k_val = 45;
		params = new ArrayList<String>();
		fillTable();
	}
	
	
	public synchronized void start_thread(){
		/*params = "--K="+k_val+" ";
		if (OptionFileName != null && !OptionFileName.equals("")) {
			params = params +"--config="+OptionFileName;
			//System.out.println(params);
		}*/
		
		params.clear();
		params.add("--K="+k_val);
		params.add("--T="+t_val);
		params.add("--ALPHA="+alphaVal);
		params.add("--config="+OptionFileName);
/*
K - iteracje i 
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
				okno.StartButton.setEnabled(false);
				terminate_thread();
				if ( proces != null )proces.destroy();
				halted = true;
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
		} 
		else if (error){
			print_debug_info("[M]No executor file...");
		}
		else if( optTable == null ){
			print_debug_info("[M]Please load config file first...");
		}
		else{
			print_debug_info("[M]No input file..");
		}
	}
	public synchronized void setFileName( String filename, File file){
		this.filename = filename;
		this.file = file;
	}
	public synchronized void signalizeFinish(){
		okno.StartButton.setText("Start");
		okno.StartButton.setEnabled(true);
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
		}else if ( solution.size()!=0 ) {
			results_list.clear();
//			/float max_val = 0.0f;
			
			for (int i=0; i<solution.size();i++ ){
				results_list.add(solution.get(i).profit);
				/*if (solution.get(i).profit > max_val) {
					best_solution = i;
					max_val = solution.get(i).profit;
				
				}*/
			}
			best_solution = solution.size()-1;
			okno.graph.setPoints(results_list);
			if ( results_list.size() != 0 ) okno.map.DrawSolution(solution.get(best_solution));
			okno.setBestSolLabel( Float.toString(solution.get(solution.size()-1).profit) );
		}
		
		//okno.StartButton.setEnabled(true)
	}
	
	public int getMaxVal(){
		return best_solution;
	}
	
	public void setKval( int value ){
		k_val = value;
	}
	
	public void setTval ( int value ){
		t_val = value;
	}
	
	public void setAplhaVal ( int value ){
		alphaVal = value;
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


	public Hashtable<String, List<Float>> getOptTable() {
		return optTable;
	}


	public void setOptTable(Hashtable<String, List<Float>> optTable) {
		// if ( this.optTable != null ) this.optTable.clear(); 
		this.optTable = optTable;
		if (filename != null && !filename.equals("")) {
			okno.map.DrawMap(file);
			okno.legend.DrawLegend();
		}
	}
	
	public void fillTable(){
		optTable = new Hashtable<String, List<Float>>();
		
		List<Float> range = new ArrayList<Float>();
		List<Float> cost = new ArrayList<Float>();
		List<Float> profit = new ArrayList<Float>();
		
		optTable.put("range", range);
		optTable.put("cost", cost);
		optTable.put("profit", profit);
		
		range.add(0.0f);
		range.add(3.0f);
		range.add(5.0f);
		range.add(6.0f);
		range.add(7.0f);
		
		cost.add(0.0f);
		cost.add(1000.0f);
		cost.add(2500.0f);
		cost.add(4500.0f);
		cost.add(5000.0f);
		
		for(int i='a';i <= 'z'; i++){
			profit.add( (i-'a'+1)*300.0f );
		}
		
	}
}
