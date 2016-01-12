package testModules;

import java.util.ArrayList;

import calculations.SkillCalculator;
import controller.Individual;

public class FeedingTrough {
	private ArrayList<Skill> skillsNeeded;
	private int amountOfFood;
	
	public FeedingTrough(int amountOfFoodProvided, ArrayList<Skill> skillsNeededToReachTrough) {
		this.amountOfFood = amountOfFoodProvided;
		this.skillsNeeded = skillsNeededToReachTrough;
	}
	
	public int getAmountOfFood() {
		return amountOfFood;
	}
	
	public boolean individualCanReachTrough(Individual individual) {
		return SkillCalculator.isIndividualMatchingRequirements(individual, skillsNeeded);
	}
	
	public boolean eat(Individual individual) {
		if(amountOfFood > 0 && individualCanReachTrough(individual)) {
			if(individual.eat()) {
				System.out.println("Someone ate: " + individual);
				amountOfFood--;
				return true;
			}
		}
		return false;
	}
}
