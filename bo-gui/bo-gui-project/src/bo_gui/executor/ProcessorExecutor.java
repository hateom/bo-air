package bo_gui.executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessorExecutor{
	private String output = "";
	private OutputParser parser;
	private List<SolutionStruct> result;

	public ProcessorExecutor(){
		/// Tworzenie parsera
		parser = new OutputParser();
		result = new ArrayList<SolutionStruct>();
	}
	
	public void Execute( String path, String params ) throws IOException{
		if (! result.isEmpty()){
			result.clear();
		}
		//TODO zrobic jakies ladne czyszczenie tego...
		output = new String("");
		
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
		//command.add("-s");
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
		while( (temp = reader.readLine()) != null){
			result.add(parser.parse(temp));
			output = output+temp+"\n";
		}
	}

	public String getOutput() {
		return output;
	}
	
	public List<SolutionStruct> getResult() {
		return result;
	}
	
	public  void freeAll(){
		
	}
}
