package bo_gui.executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bo_gui.gui.Executor_Thread_Menager;

public class ProcessorExecutor implements Runnable{
	private String output = "";
	private OutputParser parser;
	private List<SolutionStruct> result;
	private Executor_Thread_Menager menager;
	private String inFile, program_name;
	private List<String> params;
	//
	
	public ProcessorExecutor(Executor_Thread_Menager menager , String pExecutor, List<String> params, String inFile){
		/// Tworzenie parsera
		
		parser = new OutputParser();
		result = new ArrayList<SolutionStruct>();
		this.menager = menager;
		this.inFile = new String( inFile );
		this.program_name = new String( pExecutor);
		this.params = params;
	}
	/*
	public synchronized void halt(){
		menager.print_debug_info("Halt requested, please wait..");
	}
	*/
	public void run() {
		
			menager.print_debug_info("[E]Process started...");
			if (! result.isEmpty()){
				result.clear();
			}
			output = "";
			
			List<String> command = new ArrayList<String>();
			ProcessBuilder builder = new ProcessBuilder(command);
			
			//builder.directory( new File ("C:\\WINDOWS\\system32"));

			Process process = null;
			command.add(program_name);
			command.add("--silent=1");
			//command.add(params);
			for (int i=0;i<params.size();i++){
				command.add(params.get(i));
			}
			String file_param = new String ( "--input="+inFile );
			command.add( file_param );
			//menager.print_debug_info(program_name+" --silent=1"+params+" "+file_param);
			try { 
				process = builder.start();
				menager.setProcess(process);
				//process.waitFor();
				//process.exitValue();
			} catch (IOException e) {
				e.printStackTrace();
				menager.print_debug_info("[E]" + e.getMessage());
			} /*catch (InterruptedException e) {
				menager.print_debug_info("[E]Got TermSignal, terminating subprocess..");
				process.destroy();	
			}*/
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String temp;

			try {
				while( (temp = reader.readLine()) != null && !Thread.interrupted())
				{
					result.add(parser.parse(temp));
					output = output+temp+"\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
				menager.print_debug_info("[E]" + e.getMessage());
			}
			menager.setProcessResult(result);
			menager.signalizeFinish();
			menager.print_debug_info("[E]Process finished");
	}
		
}
