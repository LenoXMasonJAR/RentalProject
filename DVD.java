package project4;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DVD extends Disk { 
	
	@Override 	
	public double getCost(GregorianCalendar dat) {
		double cost = 1.2;		
		return cost;
	}
	
	public DVD() {
		super();
	}
	
	public DVD(GregorianCalendar rentedOn, GregorianCalendar dueBack, String title, String name) {
		super();
		
	}
}
