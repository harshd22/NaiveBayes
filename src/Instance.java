import java.util.ArrayList;

public class Instance {

	
	ArrayList<Integer> attributes;
	int Class;
	
	
	public Instance(ArrayList<Integer> attributes , int Class) {
		this.attributes = attributes;
		this.Class = Class;
	}


	public ArrayList<Integer> getAttributes() {
		return attributes;
	}


	public int getclass() {
		return Class;
	}


	public void setAttributes(ArrayList<Integer> attributes) {
		this.attributes = attributes;
	}


	public void setClass(int class1) {
		Class = class1;
	}
	
	
}
