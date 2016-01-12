package launcher;

import calculations.Generations;
import controller.PopulationController;
import model.Population;

public class Launcher {
	
	static int numberOfGenerationsSimulated = 1;

	public static void main(String[] args) {
		PopulationController pc = new PopulationController();
		for(int i = 0; i < numberOfGenerationsSimulated; i++ ) {
			pc.generateNextGeneration();
			pc.printPopulation();
		}
		
		Generations gc = Generations.getGenerations();
		for(int i = 0; i < numberOfGenerationsSimulated; i++) {
			Population pop = gc.getPopulation(i);
			String trend;
			if(i == 0)
				trend = "o";
			else
				trend = (pop.getAverageFitness() > gc.getPopulation(i-1).getAverageFitness()) ? "+" : 
					(pop.getAverageFitness() == gc.getPopulation(i-1).getAverageFitness()) ? "o": "-";
			System.out.println(
					String.format("%1$3s", i) 
					+ ") " 
					+ trend 
					+ " " 
					+ pop.getAverageFitness() 
					+ String.format("%1$7s", "\t")
					+ pop.getBestIndividual().toString());
		}
		
		GUI gui = new GUI(pc);
	}
}
