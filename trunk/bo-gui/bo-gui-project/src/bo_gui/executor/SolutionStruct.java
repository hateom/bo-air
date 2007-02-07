package bo_gui.executor;

import java.util.List;

public class SolutionStruct {
	public float profit = 0.0f;
	public List<AntennaStruct> transmitter;
	
	SolutionStruct(float profit, List<AntennaStruct> transmitter){
		this.profit = profit;
		this.transmitter = transmitter;
	}
}
