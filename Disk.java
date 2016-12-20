package project4;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Disk implements Serializable {

	protected PlayerType player;
	private static final long serialVersionUID = 1L;
	protected GregorianCalendar rentedOn;
	protected GregorianCalendar dueBack;
	protected String title;
	protected String nameOfRenter; 
	 	
	public double getCost(GregorianCalendar dat) {
		return 0;
	}
	
	public Disk() {
	}
	
	public Disk(GregorianCalendar rentedOn, GregorianCalendar dueBack, String title, String name, PlayerType player) {
		super();
		this.rentedOn = rentedOn;
		this.dueBack = dueBack;
		this.title = title;
		this.nameOfRenter = name;
		this.player = player;
	}
	
	public GregorianCalendar getRentedOn() {
		return rentedOn;
	}
	public void setRentedOn(GregorianCalendar opened) {
		this.rentedOn = opened;
	}
	public GregorianCalendar getDueBack() {
		return dueBack;
	}
	public void setDueBack(GregorianCalendar dueBack) {
		this.dueBack = dueBack;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNameOfRenter() {
		return nameOfRenter;
	}

	public void setNameOfRenter(String nameOfRenter) {
		this.nameOfRenter = nameOfRenter;
	}
	public PlayerType getPlayer() {
		return player;
	}

	public void setPlayer(PlayerType player) {
		this.player = player;
	}
}
