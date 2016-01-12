package model;

import java.util.Arrays;
import java.util.Collections;

import controller.Individual;

public class Population {
	
	private int DNALength;
	private Individual[] allIndividuals;
	private int generation;
	
	private Population(Population oldPopulation) {
		this.DNALength = oldPopulation.DNALength;
		this.allIndividuals = oldPopulation.allIndividuals.clone();
		this.generation = oldPopulation.generation;
	}
	
	public Population(int numberOfIndividuals, int DNALength) {
		this.allIndividuals = new Individual[numberOfIndividuals];
		this.DNALength = DNALength;
		this.generation = 0;
	}

	public int getGeneration() {
		return generation;
	}
	
	public void setGeneration(int generation) {
		this.generation = generation;
	}
	
	public void increaseGeneration() {
		this.generation++;
	}

	public int getNumberOfIndividuals() {
		return allIndividuals.length;
	}
	
	public Individual getIndividual(int index) {
		return allIndividuals[index];
	}
	
	public void setIndividual(int index, Individual individual) {
		allIndividuals[index] = individual;
	}

	public int getDNALength() {
		return DNALength;
	}
	
	public int getMinFitness() {
		int minFitness = Integer.MAX_VALUE;
		for(Individual indi : allIndividuals) {
			if(indi.getFitness() < minFitness) {
				minFitness = indi.getFitness();
			}
		}
		return minFitness;
	}
	
	public int getMaxFitness() {
		int maxFitness = Integer.MIN_VALUE;
		for(Individual indi : allIndividuals) {
			if(indi.getFitness() > maxFitness) {
				maxFitness = indi.getFitness();
			}
		}
		return maxFitness;
	}
	
	public double getAverageFitness() {
		int cummFitness = 0;
		for(Individual indi : allIndividuals) {
			cummFitness += indi.getFitness();
		}
		return cummFitness / (double) allIndividuals.length;
	}
	
	public Individual getBestIndividual() {
		int maxFitness = Integer.MIN_VALUE;
		Individual best = null;
		for(Individual indi : allIndividuals) {
			if(indi.getFitness() > maxFitness) {
				maxFitness = indi.getFitness();
				best = indi;
			}
		}
		return best;
	}
	
	public Population getCopy() {
		return new Population(this);
	}

	public void setDNALength(int dNALength) {
		DNALength = dNALength;
	}

	public Individual[] getAllIndividuals() {
		return allIndividuals;
	}

	public void setAllIndividuals(Individual[] allIndividuals) {
		this.allIndividuals = allIndividuals;
	}
	
	public void sortByFitness() {
		Arrays.sort(allIndividuals);
		Collections.reverse(Arrays.asList(allIndividuals));
	}
	
	public void dropLastPercent(int percent) {
		int cut = (int) (allIndividuals.length * (1 - percent/100.));
		for(int i = cut; i < allIndividuals.length; i++) {
			allIndividuals[i] = null;
		}
	}
}
