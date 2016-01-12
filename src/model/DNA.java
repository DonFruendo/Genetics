package model;

public class DNA {
	private String sequenz;
	
	public DNA(int length) {
		StringBuilder sequenzBuilder = new StringBuilder();
		for(int i = 0; i < length; i++) {
			sequenzBuilder.append(DNABase.values()[(int)(Math.random() * DNABase.values().length)]);
		}
		
		sequenz = sequenzBuilder.toString();
	}
	
	public DNA(String sequenz) {
		this.sequenz = sequenz;
	}
	
	public int getLength() {
		return sequenz.length();
	}
	
	public String getSequenz() {
		return sequenz;
	}
	
	public String toString() {
		return sequenz;
	}
}
