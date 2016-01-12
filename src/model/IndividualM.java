package model;
/**
public class IndividualM implements Comparable<IndividualM>{
	private DNA dna;
	private int fitness;
	
	public IndividualM(int DNALength) {
		dna = new DNA(DNALength);
	}
	
	public IndividualM(DNA dna) {
		this.dna = dna;
	}
	
	public DNA getDNA() {
		return this.dna;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	@Override
	public int compareTo(IndividualM o) {
		if(o.getFitness() > this.fitness) {
			return -1;
		}
		if (o.getFitness() < this.fitness) {
			return 1;
		}
		return 0;
	}
	
	public String toString() {
		return "Individual [" + dna.toString() + "]";
	}
}
*/