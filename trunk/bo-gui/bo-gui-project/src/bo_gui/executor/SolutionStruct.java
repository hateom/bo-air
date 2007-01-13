package bo_gui.executor;

public class SolutionStruct {
	public float profit = 0.0f;
	public AntennaStruct antenna;
	
	SolutionStruct(float profit, AntennaStruct antenna){
		this.profit = profit;
		this.antenna = antenna;
	}
}
