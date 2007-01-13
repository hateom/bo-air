package bo_gui.executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessorExecutor{
	private float zysk = 10101.21f;
	private int[] wektorAnten = null;
	private String output = "";
	
	public ProcessorExecutor(){
		
		// Uzupelnienie params
	}
	
	public void Execute( String params ) throws IOException{
		List<String> command = new ArrayList<String>();
		String [] cmd;

		ProcessBuilder builder = new ProcessBuilder(command);
		//builder.directory( new File ("C:\\WINDOWS\\system32"));
		if (System.getProperty("os.name").equalsIgnoreCase("Windows XP")){
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
			output = output+temp+"\n";
		}
		output=output+System.getProperty("user.dir")+"\n";
		output=output+System.getProperty("os.name");
	}

	public int[] getWektorAnten() {
		return wektorAnten;
	}

	public float getZysk() {
		return zysk;
	}

	public String getOutput() {
		return output;
	}
}
