package calculations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.Individual;
import model.BodyPart;
import model.CBodyPart;
import testModules.Skill;

public class SkillCalculator {
	
	@SuppressWarnings("serial")
	static Map<Skill, BodyPart> hasNeededBodyParts = new HashMap<Skill, BodyPart>(){
		{
			put(Skill.FLY, BodyPart.WING);
			put(Skill.GRAB, BodyPart.HAND);
			put(Skill.NONE, BodyPart.BODY);
		}
	};
	
	public static boolean isIndividualMatchingRequirements(Individual individual, ArrayList<Skill> requirements) {
		for(Skill skill : requirements) {
			if(!individual.hasBodyPart(hasNeededBodyParts.get(skill))) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean canFly(Individual individual) {
		ArrayList<CBodyPart> firstLevelParts = individual.getListOfBodyParts();
		int amountOfWings = 0;
		for(int i = 0; i < firstLevelParts.size(); i++) {
			if(firstLevelParts.get(i).isBodyPart(BodyPart.WING)) {
				amountOfWings++;
			}
		}
		if((amountOfWings * 3) >= individual.getCompleteListOfBodyParts().size()) {
			return true;
		}
		
		
		return false;
	}
}
