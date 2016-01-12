package calculations;

import java.util.ArrayList;

import controller.Individual;
import launcher.GlobalValues;
import model.*;

public class FitnessCalculator {
	
	public static int getFitness(Individual individual) {
		
		return -1;
	}
	
	public static void calculatePopulationFitness(Population population) {
		for(Individual individual : population.getAllIndividuals()) {
			individual.setFitness(calculateFitness(individual));
		}
	}
	
	public static int calculateFitness(Individual individual) {
		// TODO funny things to change
		
		DNA indiDNA = individual.getDNA();

		int fitness = 0;
		fitness += (int) ((indiDNA.getLength() / 10) - 5);
		
		ArrayList<CBodyPart> list = individual.getCompleteListOfBodyParts();
		for (CBodyPart part : list) {
			fitness += GlobalValues.bodyPartValues.get(part.getBodyPart()) * (part.getLayer());
		}
		
		/*
		if (individual.hasHead()) {
			fitness += 6;
		}
		if (individual.hasArms()) {
			fitness += 6;
		} //*/
		if (individual.hasBodyPart(BodyPart.ARM) && individual.hasBodyPart(BodyPart.HEAD)) {
			fitness += 25;
		}
		
		if(SkillCalculator.canFly(individual)) {
			fitness += 1000;
		}
		return fitness;
	}
	
	public static int calculateDiversity(Population population, Individual individual) {
		// TODO completion
		double[] baseratings = new double[DNABase.values().length];
		int[] baseamounts = new int[DNABase.values().length];
		for(Individual indi : population.getAllIndividuals()) {
			for(int i = 0; i < indi.getDNA().getSequenz().length(); i++) {
				char base = indi.getDNA().getSequenz().charAt(i);
				for(int j = 0; j < baseamounts.length; j++) {
					
				}
			}
		}
		
		for(int i = 0; i < baseratings.length; i++) {
			baseratings[i] = baseamounts[i] / (double) population.getNumberOfIndividuals();
		}
		
		
		return 0;
	}
}
