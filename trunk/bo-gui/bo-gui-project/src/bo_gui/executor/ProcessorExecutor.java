package bo_gui.executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bo_gui.gui.Exectuor_Thread_Menager;

public class ProcessorExecutor implements Runnable{
	private String output = "";
	private OutputParser parser;
	private List<SolutionStruct> result;
	private Exectuor_Thread_Menager menager;
	private String params;
	//
	
	public ProcessorExecutor(Exectuor_Thread_Menager menager , String param){
		/// Tworzenie parsera
		parser = new OutputParser();
		result = new ArrayList<SolutionStruct>();
		this.menager = menager;
		this.params = param;
	}
	/*
	public synchronized void halt(){
		halt = true;
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
			if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
				//cmd = {"processor.exe"};
				command.add("processor.exe");
			} else{
				command.add("./processor");
			}
			Process process = null;
			command.add("-s");
			command.add(params);
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
