package testModules;

import java.util.ArrayList;

import controller.DNAController;
import controller.Individual;
import model.DNA;

public class PopulationController {
	
	private Population population;
	private boolean speciesDead = false;
	
	
	public PopulationController(int numberOfIndividuals, int DNALength) {
		population = new Population(numberOfIndividuals, DNALength);
		createInitialPopulation();
	}
	
	private void createInitialPopulation() {
		for(int i = 0; i < population.getAllIndividuals().size(); i++) {
			Individual indiv = new Individual(population.getDefaultDNALength());
			population.getAllIndividuals().set(i, indiv);
		}
		population.getAllIndividuals().set(0, new Individual(new DNA("GACTTAGTCAGGACTTAGTCAGGACTTAGTCAGGACTTAGTCAGGACTTAGTCAG")));
		population.getAllIndividuals().set(1, new Individual(new DNA("GACTTAATCAG")));
	}
	
	private ArrayList<Individual> getAllBreeableIndividuals() {
		ArrayList<Individual> result = new ArrayList<Individual>();
		for(Individual indi : population.getAllIndividuals()) {
			if(indi.canReproduce()) {
				result.add(indi);
			}
		}
		return result;
	}
	
	private int getAmountOfFood(ArrayList<Individual> individuals) {
		int amount = 0;
		for(Individual indi : individuals) {
			amount += indi.getAmountOfFoodInStock();
		}
		return amount;
	}
	
	private Individual getRandomIndividual(ArrayList<Individual> individuals, int totalAmountOfFood, Individual mustNotBe) {
		for(int i = 0; i < individuals.size(); i++) {
			Individual indi = individuals.get(i);
			double rand = Math.random();
			double chance = indi.getAmountOfFoodInStock() / (double)totalAmountOfFood;
			if(rand < chance && indi != mustNotBe) {
				if(indi.canReproduce()) {
					return indi;
				}
			}
			if(i == individuals.size() - 1) {
				return indi;
			}
		}
		
		return null;
	}
	
	
	public void startDinner() {
		int amountFoodOnGround = population.getNumberOfIndividuals();
		ArrayList<Skill> skillList1 = new ArrayList<Skill>(); 
		skillList1.add(Skill.NONE);
		int amountFoodInAir = population.getNumberOfIndividuals();
		ArrayList<Skill> skillList2 = new ArrayList<Skill>(); 
		skillList2.add(Skill.FLY);
		ArrayList<FeedingTrough> feedingTroughs = new ArrayList<FeedingTrough>();
		
		FeedingTrough ft1 = new FeedingTrough(amountFoodOnGround, skillList1);
		FeedingTrough ft2 = new FeedingTrough(amountFoodInAir, skillList2);
		
		feedingTroughs.add(ft1);
		feedingTroughs.add(ft2);
		
		ArrayList<Individual> nextRound = population.getAllIndividuals();
		while(nextRound.size() > 0) {
			ArrayList<Individual> thisRound = nextRound; 
			nextRound = new ArrayList<Individual>();
			for(Individual indi : thisRound) {
				ArrayList<FeedingTrough> reachable = new ArrayList<FeedingTrough>();
				for(FeedingTrough ft : feedingTroughs) {
					if(ft.individualCanReachTrough(indi)) {
						reachable.add(ft);
					}
				}
				
				int maxAmount = Integer.MIN_VALUE;
				FeedingTrough desiredTarget = null;
				for(FeedingTrough ft : reachable) {
					if(ft.getAmountOfFood() > maxAmount) {
						desiredTarget = ft;
					}
				}
				if(desiredTarget != null && desiredTarget.eat(indi))
				{
					nextRound.add(indi);
				}
			}
		}
		
	}
	
	public void evolveNextGeneration() {
		ArrayList<Individual> breedableIndividuals = getAllBreeableIndividuals();
		int totalAmountOfFood = getAmountOfFood(breedableIndividuals);
		int freeSpace = totalAmountOfFood / 2;
		if(breedableIndividuals.size() > freeSpace * 1.5) {
			
			ArrayList<Individual> children = new ArrayList<Individual>();
			for(int i = 0; i < freeSpace; i++) {
				Individual parent1 = getRandomIndividual(breedableIndividuals, totalAmountOfFood / 2, null);
				Individual parent2 = getRandomIndividual(breedableIndividuals, totalAmountOfFood / 2, parent1);
				parent1.reproduce();
				parent2.reproduce();
				DNA newDNA = DNAController.mixDNA(parent1.getDNA(), parent2.getDNA());
				Individual child = new Individual(newDNA);
				children.add(child);
			}
			
			for(int i = population.getNumberOfIndividuals() - 1; i > 0; i--) {
				Individual indi = population.getAllIndividuals().get(i);
				if(!indi.canReproduce()) {
					population.getAllIndividuals().remove(i);
				}
			}
			population.getAllIndividuals().addAll(children);
		} else {
			System.out.println();
			System.out.println("Nur noch " + breedableIndividuals.size() + " Individuen übrig: (" + freeSpace + " benötigt)");
			for(Individual indi : breedableIndividuals) {
				System.out.println("> " + indi);
			}
			System.out.println("Spezies ausgerottet!");
			speciesDead = true;
		}
	}
	
	public void printPopulation() {
		if(!speciesDead) {
			System.out.println("Population:");
			for(Individual indi : population.getAllIndividuals()) {
				System.out.println(indi);
			}
			System.out.println();System.out.println();
		}
	}
	
	private class Population {
		private final ArrayList<Individual> allIndividuals;
		private final int defaultDNALength;
		
		public Population(int numberOfIndividuals, int DNALength) {
			this.defaultDNALength = DNALength;
			allIndividuals = new ArrayList<Individual>();
			for(int i = 0; i < numberOfIndividuals; i++) {
				allIndividuals.add(new Individual(DNALength));
			}
		}
		
		public int getNumberOfIndividuals() {
			return allIndividuals.size();
		}

		public int getDefaultDNALength() {
			return defaultDNALength;
		}
		
		public ArrayList<Individual> getAllIndividuals() {
			return allIndividuals;
		}
	}
}
