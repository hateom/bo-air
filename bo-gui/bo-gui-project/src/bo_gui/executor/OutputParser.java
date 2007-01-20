package bo_gui.executor;

import java.util.ArrayList;
import java.util.List;

public class OutputParser {
	
	private int start_index = 0;
	private int end_index = 0;
	private int subStr_index;
	private int count = 0;
	private float profit = 0.0f;
	private String temp_str = "";
	private int transmitter_number, transmitter_type;
	
	public OutputParser(){
		
	}
	
	public SolutionStruct parse(String input){
		
		List<AntennaStruct> trans_list = new ArrayList<AntennaStruct>();
		
		start_index = 0;
		end_index = 0;
		subStr_index = 0;
		count = 0;
		profit = 0.0f;
		temp_str = "";
		
		int size = input.length();

		
		do {
			end_index = input.indexOf(';',start_index);
			temp_str = input.substring(start_index, end_index);
			if ( count == 0){
				profit = Float.valueOf(temp_str);
			}
			else{
				subStr_index = temp_str.indexOf(':');
				transmitter_number = Integer.valueOf(temp_str.substring(0,subStr_index));
				transmitter_type = Integer.valueOf(temp_str.substring(subStr_index+1));
				trans_list.add( new AntennaStruct(transmitter_number, transmitter_type));
			}
			start_index = end_index+1;
			count++;
		} while( start_index < size);
		SolutionStruct temp = new SolutionStruct(profit, trans_list);
		return temp;
	}
	
	public void freeAll(){
		
	}

}
