package calculations;

import java.util.HashMap;
import java.util.Map;

import model.Population;

public class Generations {
	private static Generations singleton;
	private Map<Integer, Population> generations;
	
	private Generations() {
		this.generations = new HashMap<Integer, Population>();
	}
	
	public static Generations getGenerations() {
		if(singleton == null) {
			singleton = new Generations();
		}
		return singleton;
	}
	
	public void addGeneration(int genIndex, Population population) {
		generations.put(genIndex, population);
	}
	
	public Population getPopulation(int generation) {
		return generations.get(generation);
	}
}
