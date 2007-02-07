package bo_gui.executor;

public class AntennaStruct {
	
	public int index;
	public int range;
	public int type;
	public float cost;
	
	public AntennaStruct(int index, int type, int range, float cost){
		this.index = index;
		this.range = range;
		this.type = type;
		this.cost = cost;
	}
//backward compatibilty	
	public AntennaStruct(int index, int type){
		this.index = index;
		this.range = 0;
		this.type = type;
		this.cost = 0.0f;
	}

}
