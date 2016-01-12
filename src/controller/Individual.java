package controller;

import java.util.ArrayList;

import launcher.GlobalValues;
import model.BodyPart;
import model.CBodyPart;
import model.DNA;

public class Individual  implements Comparable<Individual>{
	IndividualModel model;
	private int foodInStock = 0; 
	
	public Individual(int DNALength) {
		model = new IndividualModel(DNALength);
	}
	
	public Individual(DNA dna) {
		model = new IndividualModel(dna);
	}
	
	public DNA getDNA() {
		return model.getDNA();
	}
	
	public int getFitness() {
		return model.getFitness();
	}
	
	public void setFitness(int fitness) {
		model.setFitness (fitness);
	}
	
	public boolean eat() {
		if(foodInStock < GlobalValues.maxAmountFood) {
			foodInStock ++;
			return true;
		}
		return false;
	}
	
	public boolean reproduce() {
		if(foodInStock > 0) {
			foodInStock--;
			return true;
		}
		return false;
	}
	
	public int getAmountOfFoodInStock() {
		return foodInStock;
	}
	
	public boolean canReproduce() {
		return (foodInStock > 0);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<CBodyPart> getListOfBodyParts() {
		return (ArrayList<CBodyPart>) model.listOfBodyParts.clone();
	}
	
	public ArrayList<CBodyPart> getCompleteListOfBodyParts() {
		ArrayList<CBodyPart> partsInParts = new ArrayList<CBodyPart>();
		for(CBodyPart cpart : model.listOfBodyParts) {
			partsInParts.add(cpart);
			partsInParts.addAll(cpart.getCompleteListOfBodyParts());
		}
		return partsInParts;
	}
	
	public boolean hasBodyPart(BodyPart part) {
		if(part == BodyPart.BODY) {
			return true;
		}
		for(CBodyPart cbp : model.listOfBodyParts) {
			if(cbp.isBodyPart(part)) {
				return true;
			}
		}
		return false;
	}
	
	
	private class Body extends CBodyPart {
		public Body(String dna) {
			super(dna, null);
			this.part = BodyPart.BODY;
			this.listOfBodyParts = CBodyPart.findAllBodyPartsIn(this);
		}
	}
	
	private class IndividualModel implements Comparable<IndividualModel>{
		private DNA dna;
		private int fitness;
		private CBodyPart body;
		private ArrayList<CBodyPart> listOfBodyParts;
		private ArrayList<BodyPart> alreadyAttached;
		
		public IndividualModel(int DNALength) {
			this(new DNA(DNALength));
//			this.body = new CBodyPart(dna.getSequenz(), null);
//			this.listOfBodyParts = body.getListOfBodyParts();
//			for(CBodyPart part : listOfBodyParts) {
//				part.setLayer(1);
//			}
		}
		
		public IndividualModel(DNA dna) {
			this.dna = dna;
			this.body = new Body(dna.getSequenz());
			this.listOfBodyParts = body.getListOfBodyParts();
			for(CBodyPart part : listOfBodyParts) {
				part.setLayer(1);
			}
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
		public int compareTo(IndividualModel o) {
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


	public IndividualModel getModel() {
		return model;
	}

	@Override
	public int compareTo(Individual o) {
		return model.compareTo(o.getModel());
	}
	
	public String toString() {
		return model.fitness + "[Body >> " + model.listOfBodyParts + "] - " + model.dna;
		//return ((hasBodyPart(BodyPart.HEAD))? "+Head " : "") + ((hasBodyPart(BodyPart.ARM))? "+Arms " : "") + model.toString();
	}
}
