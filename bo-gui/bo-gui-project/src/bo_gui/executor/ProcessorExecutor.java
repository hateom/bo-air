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
	
	public ProcessorExecutor(Exectuor_Thread_Menager menager ){
		/// Tworzenie parsera
		parser = new OutputParser();
		result = new ArrayList<SolutionStruct>();
		this.menager = menager;
	}
	/*
	public void Execute( String path, String params ) throws IOException{
		
	}

	public String getOutput() {
		return output;
	}
	
	public List<SolutionStruct> getResult() {
		return result;
	}
	
	public  void freeAll(){
		
	}
*/
	public void run() {
		//try 
		//{
			// TODO Auto-generated method stub
			if (! result.isEmpty()){
				result.clear();
			}
			//TODO zrobic jakies ladne czyszczenie tego...
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
			command.add("input_data");
			try {
				//process = Runtime.getRuntime().exec(cmd);
				process = builder.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String temp;
			try {
				while( (temp = reader.readLine()) != null)
				{
					result.add(parser.parse(temp));
					output = output+temp+"\n";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			menager.zrobcos("Jestem procesem!!");
		//}
		/*
		catch (InterruptedException e) 
		{
			return;
		}*/
		}
		
}
