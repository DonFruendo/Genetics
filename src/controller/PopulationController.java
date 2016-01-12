package controller;

import java.util.ArrayList;
import java.util.Arrays;

import calculations.FitnessCalculator;
import calculations.Generations;
import launcher.GlobalValues;
import model.DNA;
import model.Population;

public class PopulationController {
	private Population population;	
	
	public PopulationController() {
		population = new Population(10000, 100);
		createInitialPopulation();
		//*
		population.getAllIndividuals()[0] = 
				new Individual(new DNA("GACTACGGCATCAG"	//HEAD-
				
				+"GACTTACG"			//ARM1
					
					+"GACTTAA"		//HAND
						+"GACTTCGAGCTTCAG"//FINGER-
					+"AATTCAG"		//HANDEND
					
					+"GACTTAAAATTCAG"//HAND-
				+"CCATTCAG"			//ARM1END
				+"G"					
				+"GACTTAC"			//ARM2
					+"GACTTAAAATTCAG"//HAND-
					+"GACTTAAAATTCAG"//HAND-
				+"CGGCATTCAG"		//ARM2END
				
				+"GACTGCT"			//LEG
					+"GACTGCCCCGTCAG"//FOOT-
					+"GACTGCCATCCGTCAG"//FOOT-
				+"TCGTCAG"			//LEGEND
				)); //*/
		printPopulation();
	}
	
	private void createInitialPopulation() {
		for(int i = 0; i < population.getAllIndividuals().length; i++) {
			Individual indiv = new Individual(population.getDNALength());
			population.getAllIndividuals()[i] = indiv;
		}
		FitnessCalculator.calculatePopulationFitness(population);
	}
	
	public void printGeneration() {
		System.out.println("Generation: " + population.getGeneration());
	}
	
	public void printPopulation() {
		population.sortByFitness();
		System.out.println("Population (Generation " + population.getGeneration() + "):");
		int cummFitness = 0;
		int maxFitness = 0;
		for(Individual indi : population.getAllIndividuals()) {
			if(indi.getFitness() > maxFitness) {
				maxFitness = indi.getFitness();
			}
			cummFitness += indi.getFitness();
		}
		System.out.println("Max Fitness: " + maxFitness);
		System.out.println("Average Fitness: " + cummFitness / (double)population.getNumberOfIndividuals());
		
		System.out.println("Best Individual:");
		Individual best = population.getIndividual(0);
		System.out.println(best + " - Fitness: " + best.getFitness());
		System.out.println();
		for(int i = 0; i < 10; i++) {
			System.out.println(i + " " + population.getIndividual(i));
		}
		System.out.println();
	}
	
	public void generateNextGeneration() {
		// setting fitness values - just to be sure
		FitnessCalculator.calculatePopulationFitness(population);
		// sorting
		population.sortByFitness();
		
		// saving population
		Generations.getGenerations().addGeneration(population.getGeneration(), population.getCopy());
		
		// creating new population
		Population newPop = new Population(population.getNumberOfIndividuals(), population.getDNALength());
		ArrayList<Individual> oldPop = new ArrayList<Individual> (Arrays.asList(population.getAllIndividuals()));
		int amountStayingAlive = (int) (population.getNumberOfIndividuals() * (1 - GlobalValues.cutoffPercent/100.));
		int start = 0;
		// checking for best
		if (GlobalValues.elite) {
			// keep Fittest
			if(GlobalValues.keepFittest) {
				start += GlobalValues.eliteCount;
				for (int i = 0; i < GlobalValues.eliteCount; i++) {
					newPop.setIndividual(i, oldPop.get(0));
					oldPop.remove(0);
				}
			}
			
			/*if(GlobalValues.keepLongestDNA) {
				start += GlobalValues.eliteCount;
				for (int i = 0; i < GlobalValues.eliteCount; i++) {
					newPop.setIndividual(i, oldPop.get(i));
					oldPop.remove(i);
				}
			}*/
		}
		
		
		Selection: for (int i = start; i <= amountStayingAlive; i++) {
			for (int j = 0; j < oldPop.size() - 1; j++) {
				boolean pick = (Math.random() < 0.05);
				if (pick) {
					newPop.setIndividual(i, oldPop.get(j));
					oldPop.remove(j);
					continue Selection;
				}
			}
			// take last individual
			newPop.setIndividual(i, oldPop.get(i));
			oldPop.remove(i);
		}
		newPop.setGeneration(population.getGeneration());
		population = newPop;

		// drop least fit
		//population.dropLastPercent(cutoffPercent);
		
		// mix
		for(int i = 0; i < population.getNumberOfIndividuals(); i++) {
			if(population.getIndividual(i) == null) {
				
				int rand1 = (int)(Math.random() * i);
				int rand2 = (int)(Math.random() * i);
				Individual parent1 = population.getIndividual(rand1);
				Individual parent2 = population.getIndividual(rand2);
				//*
				for (int j = 0; j < i; j++) {
					boolean pick = (Math.random() < 0.05);
					if (pick) {
						parent1 = population.getIndividual(j);
						break;
					}
				}
				for (int j = 0; j < i; j++) {
					boolean pick = (Math.random() < 0.05);
					if (pick) {
						parent2 = population.getIndividual(j);
						break;
					}
				}//*/
				DNA newDNA = DNAController.mixDNA(parent1.getDNA(), parent2.getDNA());
				Individual child = new Individual(newDNA);
				population.setIndividual(i, child);
			}
		}
		
		// mutation
		for(int i = 0; i < population.getNumberOfIndividuals(); i++) {
			int random = (int) Math.round(Math.random() * 100);
			if(random < GlobalValues.mutationRate) {
				Individual indi = population.getIndividual(i);
				DNA newDNA = DNAController.applyMutation(indi.getDNA());
				indi = new Individual(newDNA);
				population.setIndividual(i, indi);
			}
		}
		
		// setting fitness values
		FitnessCalculator.calculatePopulationFitness(population);
		population.increaseGeneration();
	}
	
	public void generateNextNGenerations(int n) {
		for(int i = 0; i < n; i++) {
			generateNextGeneration();
			printPopulation();
		}
	}
}
