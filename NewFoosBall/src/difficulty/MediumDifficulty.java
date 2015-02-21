package difficulty;

public class MediumDifficulty implements Difficulty {

	public static final double selfError = 0.05;
	public static final double compError = 0.05;
	
	public double getSelfError(){
		return this.selfError;
	}
	
	public double getCompError(){
		return this.compError;
	}
	
//	Intermediate level – The margin of error for passes and shoots for computer is equal to that of
//	user.

}
