
package project4;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**********************************************************************
 * This is the ListEngine class which sets up the JTable and also invokes the
 * methods add, get, and remove from MyLinkedList adding functionality to the
 * JTable.
 * 
 * 
 * @author Mason Mahoney
 * @author Zachary Hern
 * @version 11/29/2016
 **********************************************************************/
public class ListEngine extends AbstractTableModel {

	/** Linked List variable named disks */
	private MyLinkedList<Disk> disks;

	/** columns array for the JTable */
	private String[] columns = { "Name: ", "Title: ", "RentedOn: ", "Due Back: ", "Disk Type: " };

	/*****************************************************************
	 * Constructor for the ListEngine.
	 *****************************************************************/
	public ListEngine() {
		super();
		disks = new MyLinkedList<Disk>();
	}

	/************************************************************
	 * remove method for the disk that calls from the linked list.
	 * 
	 * @param int
	 *            i
	 * @return the disk that is removed.
	 ************************************************************/
	public Disk remove(int i) {
		Disk unit = disks.remove(i);
		fireIntervalRemoved(this, 0, disks.size() - 1);
		return unit;
	}

	/************************************************************
	 * Assists in adding the Disk to the JTable
	 * 
	 * @param ListEngine
	 *            listEngine
	 * @param int
	 *            i
	 * @param int
	 *            size
	 ************************************************************/
	private void fireIntervalRemoved(ListEngine listEngine, int i, int size) {

	}

	/************************************************************
	 * This is a method that adds the Disk to the linked list by calling the add
	 * method from the linked list.
	 * 
	 * @param Disk
	 *            a
	 ************************************************************/
	public void add(Disk a) {
		disks.add(a);
		fireIntervalAdded(this, 0, disks.size() - 1);
	}

	/************************************************************
	 * Assists in removing the Disk from the JTable
	 * 
	 * @param ListEngine
	 *            listEngine
	 * @param int
	 *            i
	 * @param int
	 *            size
	 ************************************************************/
	private void fireIntervalAdded(ListEngine listEngine, int i, int size) {

	}

	/************************************************************
	 * Returns a disk at a given index.
	 * 
	 * @param int
	 *            i
	 * @return the Disk at that given index.
	 ************************************************************/
	public Disk get(int i) {
		return disks.get(i);
	}

	/************************************************************
	 * The size of the linked list.
	 * 
	 * @return the amount of disks in linked list.
	 ************************************************************/
	public int getSize() {
		return disks.size();
	}

	// not used.... but interesting and it does work

	/************************************************************
	 * Saves and serializes the Account objects from the JTable.
	 * 
	 * @param filename
	 *            name of the file to load from.
	 ************************************************************/
	public void saveDatabase(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(disks);
			os.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Error in saving db");

		}
	}

	/********************************************************************
	 * Loads (deserializes) the Account objects from the specified file.
	 * 
	 * @param filename
	 *            name of the file to load from.
	 *******************************************************************/
	public void loadDatabase(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream is = new ObjectInputStream(fis);

			disks = (MyLinkedList<Disk>) is.readObject();
			fireIntervalAdded(this, 0, disks.size() - 1);
			is.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error in loading db");
		}
	}

	public boolean saveAsText(String filename) {
		if (filename.equals("")) {
			return false;
		}

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

			for (int i = 0; i < disks.size(); i++) {
				Disk unit = disks.get(i);
				out.println(unit.getClass().getName());
				out.println(DateFormat.getDateInstance(DateFormat.SHORT).format(unit.getRentedOn().getTime()));
				out.println(DateFormat.getDateInstance(DateFormat.SHORT).format(unit.getDueBack().getTime()));
				out.println(unit.getNameOfRenter());
				out.println(unit.getTitle());

				out.println((unit).getPlayer());

			}
			out.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	/**************************************************************************
	 * Loads the text from the file that had been saved.
	 * 
	 * @param filename
	 *            name of the file to load from.
	 *
	 **************************************************************************/
	public void loadFromText(String filename) {
		ArrayList<String> fileTxt = new ArrayList<String>();
		File loadfile;
		Scanner filein;
		MyLinkedList list = new MyLinkedList();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date daterentedOn, dateDue;

		// Attempt to create the scanner
		try {
			loadfile = new File(filename);
			filein = new Scanner(loadfile);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

		String line;
		String properties[];

		// adding lines from file into array list type String
		while (filein.hasNext())
			fileTxt.add(filein.nextLine());

		// Parse and create a dvd
		try {

			// creating disk and pulling information from text
			Disk disk;
			GregorianCalendar rentedOn;
			GregorianCalendar dueBack;
			String rentedOnTxt;
			String title;
			String name;
			String player;
			String dueBackTxt;

			int originalSize = fileTxt.size();
			for (int i = 0; i < originalSize / 6; i++) {
				fileTxt.remove(0);
				disk = new Disk();

				rentedOnTxt = fileTxt.remove(0);
				dueBackTxt = fileTxt.remove(0);

				daterentedOn = df.parse(rentedOnTxt);
				dateDue = df.parse(dueBackTxt);

				rentedOn = new GregorianCalendar();
				dueBack = new GregorianCalendar();

				rentedOn.setTime(daterentedOn);
				dueBack.setTime(dateDue);
				title = fileTxt.remove(0);
				name = fileTxt.remove(0);
				player = fileTxt.remove(0);

				disk.setDueBack(dueBack);
				disk.setRentedOn(rentedOn);
				disk.setTitle(title);
				disk.setNameOfRenter(name);
				disk.setPlayer(PlayerType.valueOf(player));
				list.add(disk);
			}
			disks = list;
			fireIntervalAdded(this, 0, disks.size() - 1);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

		// Close the file
		filein.close();
	}

	@Override
	public int getRowCount() {

		return disks.size();
	}

	@Override
	public int getColumnCount() {

		return columns.length;
	}

	@Override
	public Object getValueAt(int rowI, int colI) {
		Object val = null;

		switch (colI) {
		case 0:
			val = "RentedOn: "
					+ DateFormat.getDateInstance(DateFormat.SHORT).format(disks.get(rowI).getRentedOn().getTime());

			break;
		case 1:
			val = "DueBack: "
					+ DateFormat.getDateInstance(DateFormat.SHORT).format(disks.get(rowI).getDueBack().getTime());
			break;
		case 2:
			val = "Name: " + disks.get(rowI).getNameOfRenter();
			break;
		case 3:
			val = "Title: " + disks.get(rowI).getTitle();
			break;
		case 4:
			val = " Disk Type: " + disks.get(rowI).getPlayer();
			break;
		default:
			return null;

		}

		return val;
	}

}