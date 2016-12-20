package project4;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Game extends DVD {

	//private PlayerType player;   // Xbox 360, PS3, Xbox720, DVD.
	@Override
	public double getCost(GregorianCalendar dat) {		
		double cost = 5;		
		return cost;
	}

	public Game() {
		super();
	}
	
	public Game(GregorianCalendar rentedOn, GregorianCalendar dueBack, String title, String name, PlayerType player){
		super();

}
}
