package spintowin;

import java.util.Random; 

public class RandomNumber {
	
	public static int generateNumber() {
		 Random random = new Random();
         int randomNumber = random.nextInt(37);
         return randomNumber;
	}
}