package testModules;

public class TestLauncher {
	
	public static void main(String[] args) {
		PopulationController pop = new PopulationController(100, 1000);
		
		pop.printPopulation();
		pop.startDinner();
		pop.evolveNextGeneration();
		pop.printPopulation();
	}
}
