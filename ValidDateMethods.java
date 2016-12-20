package project4;

import java.io.*;
import java.util.*;

/*******************************************************************************
 * @author Zack
 *
 ******************************************************************************/
public class ValidDateMethods {
public static class VALID{
	
	/** years in timer */
	private int years;

	/** months in timer */
	private int months;

	/** days in timer */
	private int days;

	/** Array list holding month names */
	private String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	/** Holds list of days in each month without leap year */
	protected int[] DAYS_IN_MONTHS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	private final int DAYS_IN_LEAP_YEAR = 366;
	private final int DAYS_IN_YEAR = 365;
	static Calendar todaysDate = Calendar.getInstance();

	/**************************************************************************
	 * Default constructor
	 **************************************************************************/
	public VALID() {
		this.years = 0;
		this.months = 0;
		this.days = 0;
	}

	/**************************************************************************
	 * Constructor that takes years, months, days
	 * 
	 * @param years:
	 *            the years in the timer
	 * @param months:
	 *            the months in the timer
	 * @param days:
	 *            the days in the timer
	 **************************************************************************/
	public VALID(int months, int days, int years) {
		if (!isValidDate(months, days, years))
			throw new IllegalArgumentException();
		this.years = years;
		this.months = months;
		this.days = days;
		daysInMonth(this);
	}

	/**************************************************************************
	 * Getter for integer years
	 * 
	 * @return years: the years in the timer
	 *************************************************************************/
	public int getYears() {
		return this.years;
	}

	/**************************************************************************
	 * setter for integer years
	 * 
	 * @param years:
	 *            the years in the timer
	 *************************************************************************/
	public void setYears(int years) {
		if (!isValidDate(this.months, this.days, years))
			throw new IllegalArgumentException();
		this.years = years;
		daysInMonth(this);
	}

	/**************************************************************************
	 * getter for integer months
	 * 
	 * @return months: the months in the timer
	 *************************************************************************/
	public int getMonths() {
		return months;
	}

	/**************************************************************************
	 * setter for months
	 * 
	 * @param months
	 *************************************************************************/
	public void setMonths(int months) {
		if (!isValidDate(months, this.days, this.years))
			throw new IllegalArgumentException();
		this.months = months;
	}

	/**************************************************************************
	 * getter for integer days
	 * 
	 * @return days: the days in the timer
	 *************************************************************************/
	public int getDays() {
		return days;
	}

	/**************************************************************************
	 * setter for integer days
	 * 
	 * @param days:
	 *            the days in the timer
	 *************************************************************************/
	public void setDays(int days) {
		if (!isValidDate(this.months, days, this.years))
			throw new IllegalArgumentException();
		this.days = days;
	}

	/**************************************************************************
	 * Constructor that sets current VALID to new other
	 * 
	 * @param other:
	 *            External VALID
	 *************************************************************************/
	public VALID(VALID other) {
		if (!isValidDate(other.getMonths(), other.getDays(), other.getYears()))
			throw new IllegalArgumentException();
		this.years = other.getYears();
		this.months = other.getMonths();
		this.days = other.getDays();
		daysInMonth(this);
	}

	/**************************************************************************
	 * Constructor that takes a date and sets years, months, days accordingly
	 * 
	 * @param geoDate:
	 *            inputed date in xx/xx/xxxx format
	 **************************************************************************/

	public VALID(String geoDate) {
		if(!isValidDateString(geoDate))
			throw new IllegalArgumentException();
		String[] s = geoDate.split("/");
		if (!isValidDate(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])))
			throw new IllegalArgumentException();
		this.days = Integer.parseInt(s[1]);
		this.months = Integer.parseInt(s[0]);
		this.years = Integer.parseInt(s[2]);
	}

	/*************************************************************************
	 * returns true if current VALID is equal to object
	 * 
	 * @param other:
	 *            object passed to method
	 *************************************************************************/
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other instanceof VALID) {

			VALID otherCast = (VALID) other;
			if ((this.years == otherCast.years) && (this.months == otherCast.months) && (this.days == otherCast.days))
				return true;
			else
				return false;
		} else
			throw new IllegalArgumentException();
	}

	public int compareTo(VALID other) {
		if (this.getYears() < other.getYears())
			return -1;
		else if (this.getYears() > other.getYears())
			return 1;
		else {
			if (this.getMonths() < other.getMonths())
				return -1;
			else if (this.getMonths() > other.getMonths())
				return 1;
			else {
				if (this.getDays() < other.getDays())
					return -1;
				else if (this.getDays() > other.getDays())
					return 1;
				else
					return 0;
			}
		}
	}

	public void dec(int days) {
		VALID today = new VALID(
				todaysDate.get(Calendar.MONTH) + 1, 
				todaysDate.get(Calendar.DAY_OF_MONTH),
				todaysDate.get(Calendar.YEAR));
		if (days < 0 || days > toDays(this) - toDays(today))
			throw new IllegalArgumentException();
		while (days > 0) {
			this.days--;
			if (this.days == 0) {
				this.months--;
				if (this.months > 0) {
					this.days = DAYS_IN_MONTHS[this.months - 1];
				} else {
					this.months = 12;
					this.years--;
					daysInMonth(this);
					this.days = DAYS_IN_MONTHS[this.months - 1];
				}
			}
			days--;
		}
	}

	public void dec() {
		dec(1);
	}

	public void inc(int days) {
		if (days < 0)
			throw new IllegalArgumentException();
		while (days > 0) {
			this.days += 1;

			if (this.days > DAYS_IN_MONTHS[this.months - 1]) {
				if (this.months == 12) {
					this.months = 1;
					this.years++;
					daysInMonth(this);
				} else {
					this.months++;
				}
				this.days = 1;
			}
			days--;
		}
	}

	public void inc() {
		inc(1);
	}

	public String toString() {
		return MONTHS[this.months - 1] + " " + this.days + ", " + this.years;
	}

	public String toDateString() {
		return this.months + "/" + this.days + "/" + this.years;
	}

	public void save(String fileName) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		out.println(this.toDateString());
		out.close();
	}

	public void load(String fileName) {
		String date;
		String[] s;

		try {
			// open the data file
			Scanner fileReader = new Scanner(new File(fileName));

			// read one String in of data and an int
			date = fileReader.next();
			if (!isValidDateString(date)) {
				fileReader.close();
				throw new IllegalArgumentException();
			}
			s = date.split("/");
			this.setMonths(Integer.parseInt(s[0]));
			this.setDays(Integer.parseInt(s[1]));
			this.setYears(Integer.parseInt(s[2]));
			fileReader.close();
		}

		// could not find file
		catch (Exception error) {
			System.out.println("File not found or not valid date");
		}

	}

	public int daysToGo(String fromDate) {
		if(!isValidDateString(fromDate))
			throw new IllegalArgumentException();
		String[] s = fromDate.split("/");
		if (!isValidDate(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])))
			throw new IllegalArgumentException();
		VALID g = new VALID(Integer.parseInt(s[0]), Integer.parseInt(s[1]),
				Integer.parseInt(s[2]));
		if (toDays(g) > toDays(this))
			throw new IllegalArgumentException();
		return toDays(this) - toDays(g);
	}

	protected static boolean isValidDate(int months, int days, int years) {
		int[] TEMP_DAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (isLeapYear(years))
			TEMP_DAYS[1] = 29;
		else
			TEMP_DAYS[1] = 28;
		if (months < 13 && months > 0 && days <= TEMP_DAYS[months - 1] && days > 0
				&& years >= 1900) {
			return true;
//			if((years > todaysDate.get(Calendar.YEAR)) ||
//					(months > 1 + todaysDate.get(Calendar.MONTH)) ||
////					(months >= 1 + todaysDate.get(Calendar.MONTH) &&
////					days >= todaysDate.get(Calendar.DAY_OF_MONTH)))
//				return true;
//			else
//				return false;
		} else
			return false;
	}

	private static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// could passs
	// x/x/xxxx
	// x/xx/xxxx
	// xx/xx/xxxx
	// xx/x/xxxx
	 static boolean isValidDateString(String s){
		if(s == null)
			throw new NullPointerException();
		if(!s.contains("/"))
			return false;
		if(s.length() < 8)
			return false;
		String[] a = s.split("/");
		if(a.length > 3)
			return false;
		if(a[0].isEmpty() || !isInteger(a[0]) || a[0].length() > 2)
			return false;
		if(a[1].isEmpty() || !isInteger(a[1]) || a[1].length() > 2)
			return false;
		if(a[2].isEmpty() || !isInteger(a[2]))
			return false;
		if(!isValidDate(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2])))
			return false;
		else
			return true;
	}

	protected int toDays(VALID d){
		int leapYears = d.getYears()/4 + d.getYears()/400 - d.getYears()/100;
		int nonLeap = d.getYears() - leapYears;
		int monthNum = 0;
		for (int i = 0; i < d.getMonths()-1; i++) {
			monthNum += d.DAYS_IN_MONTHS[i];
		}
		if(isLeapYear(d.getYears())){
			leapYears--;
			nonLeap++;
		}
		return (leapYears * DAYS_IN_LEAP_YEAR) + (nonLeap * DAYS_IN_YEAR) +
				monthNum + d.getDays();
	}

	protected static boolean isLeapYear(int year) {
		if (year < 1900)
			throw new IllegalArgumentException();
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			return true;
		} else
			return false;
	}

	protected void daysInMonth(VALID g) {
		if (isLeapYear(g.getYears()))
			g.DAYS_IN_MONTHS[1] = 29;
		else {
			g.DAYS_IN_MONTHS[1] = 28;
		}
	}
	
}
}
