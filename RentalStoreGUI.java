package project4;


import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class RentalStoreGUI extends JFrame implements ActionListener {

    private JMenuBar menus;

    private JMenu fileMenu;
    private JMenu actionMenu;

    // fileMenu
    private JMenuItem openSerItem;
    private JMenuItem exitItem;
    private JMenuItem saveSerItem;
    private JMenuItem openTextItem;
    private JMenuItem saveTextItem;
    private JMenuItem clear;

    // auto Menu
    private JMenuItem rentDvdItem;
    private JMenuItem rentGameItem;
    private JMenuItem returnItem;
    boolean filter;


    private JTable jListArea;

    private ListEngine dList;

    //private
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public RentalStoreGUI() {
        fileMenu = new JMenu("File");
        openSerItem = new JMenuItem("Open Serial...");
        saveSerItem = new JMenuItem("Save Serial...");
        exitItem = new JMenuItem("Exit!");
        openTextItem = new JMenuItem("Open Text...");
        saveTextItem = new JMenuItem("Save Text...");

        fileMenu.add(openSerItem);
        fileMenu.add(saveSerItem);
        fileMenu.addSeparator();
        fileMenu.add(openTextItem);
        fileMenu.add(saveTextItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        openSerItem.addActionListener(this);
        exitItem.addActionListener(this);
        saveSerItem.addActionListener(this);
        openTextItem.addActionListener(this);
        saveTextItem.addActionListener(this);

        actionMenu = new JMenu("Action");

        rentDvdItem = new JMenuItem("Rent DVD");
        rentGameItem = new JMenuItem("Rent Game");
        returnItem = new JMenuItem("Return");
        clear = new JMenuItem("Clear");
        

        actionMenu.add(rentDvdItem);
        actionMenu.add(rentGameItem);
        actionMenu.addSeparator();
        actionMenu.add(returnItem);
        actionMenu.addSeparator();
        actionMenu.add(clear);
        

        rentDvdItem.addActionListener(this);
        rentGameItem.addActionListener(this);
        returnItem.addActionListener(this);

        menus = new JMenuBar();

        menus.add(fileMenu);
        menus.add(actionMenu);

        setJMenuBar(menus);
        dList = new ListEngine();
        jListArea = new JTable(dList);

        add(jListArea);

        setVisible(true);
        setSize(800, 400);
    }

    public static void main(String[] args) {
        new RentalStoreGUI();
    }

    public void actionPerformed(ActionEvent e) {

        JComponent comp = (JComponent) e.getSource();

        if (exitItem == comp)
            System.exit(0);

        if (openSerItem == comp || openTextItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (openSerItem == comp)
                    dList.loadDatabase(filename);
                if (openTextItem == e.getSource())
                    dList.loadFromText(filename);
            }
        }

        if (saveSerItem == comp || saveTextItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (saveSerItem == e.getSource())
                    dList.saveDatabase(filename);
                if (saveTextItem == e.getSource())
                    dList.saveAsText(filename);
            }
        }
        if (rentDvdItem == comp) {
            DVD dvd = new DVD();
            RentDVDDialog x = new RentDVDDialog(this, dvd);	
            if (x.closeOK())
                dList.add(dvd);
        }


        if (rentGameItem == comp) {
            Game game = new Game();
            RentGameDialog x = new RentGameDialog(this, game);
            if (x.closeOK())
                dList.add(game);
        }
	
  
        if (returnItem == e.getSource()) {
        	try {
        		if(jListArea.getSelectedRow() == -1)
            		throw new IllegalArgumentException();
            int index = jListArea.getSelectedRow();
            if (index != -1) {
            	GregorianCalendar today = new GregorianCalendar();
            	GregorianCalendar dueDate = new GregorianCalendar();
            	dueDate.setTime(dList.get(index).getDueBack().getTime());
            	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            	
            	try {
					today.setTime(dateFormat.parse(DateFormat.
							getDateInstance(DateFormat.SHORT).
							format(today.getTime())));
				} catch (ParseException c) {
					throw new IllegalArgumentException();
				}

				double cost = 0.0;
				double lateFee = 0.0;

				if(dList.get(index).getPlayer() == PlayerType.DVD) {   // checking to see if its a game .getGame()??
					cost = 1.2;
					lateFee = 0.0;

					//If the title is late
					if(today.after(dueDate))
						lateFee = 2.0;
				}
				else {
					cost = 5.0;
					lateFee = 0.0;

					//If the title is late
					if(today.after(dueDate))
						lateFee = 10.0;
				}
				//Remove the unit from the database 
				
				
				String thankYouString = "Thank you, " + 
						dList.get(index).getNameOfRenter()
								+ ", for renting " + 
								dList.get(index).getTitle() + ".\n";
		
						DecimalFormat df = new DecimalFormat("#0.00");
		
						String costString = "Rental cost: $" + 
								df.format(cost) + "\n";
						String lateFeeString = "Late fee: $" + 
								df.format(lateFee) + "\n";
						String totalCostString = "Total cost: $" + 
								df.format(cost + lateFee) + "\n";
						
						Disk unit = dList.remove(index);
		
						JOptionPane.showMessageDialog(null, thankYouString +
								costString + lateFeeString + totalCostString);
						
						//Remove the unit from the database          	
						//Disk unit = dList.remove(index);
				
//					JOptionPane.showMessageDialog(null, "Thanks " + unit.getNameOfRenter() + 
//							"\n for returning " + unit.getTitle() + ", you owe: " +  unit.getCost(today) +
//							" dollars");


            }
            
        	}catch(NullPointerException n){
        		JOptionPane.showMessageDialog(this, "There is nothing left to return!");
        	}
        	catch(IllegalArgumentException a){
        		JOptionPane.showMessageDialog(this, "Please Select an Option or Rent DVD/Game");
        	}
        }
    }
}

