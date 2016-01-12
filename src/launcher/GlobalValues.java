package launcher;

import java.util.HashMap;
import java.util.Map;

import model.BodyPart;

public class GlobalValues {
	public static double pickProbability = 0.05;
	public static int maxAmountFood = 5;
	
	public static int mutationRate = 50;
	public static int mutationDNAAppend = 10;
	
	public static int cutoffPercent = 20;
	public static boolean elite = true;
	public static boolean keepFittest = true;
	public static boolean keepMostDiverse = true;
	public static boolean keepLongestDNA = true;
	public static int eliteCount = 3;

	
	@SuppressWarnings("serial")
	public static Map<BodyPart, Integer> bodyPartValues = new HashMap<BodyPart, Integer>(){
		{
			put(BodyPart.HEAD, 40);
			
			put(BodyPart.ARM, 30);
			put(BodyPart.WING, 30);
			put(BodyPart.LEG, 30);
			
			put(BodyPart.MOUTH, 20);
			put(BodyPart.FOOT, 20);
			put(BodyPart.HAND, 20);
			
			put(BodyPart.FINGER, 15);
			
			
			put(null, 0);
		}
	};
}
