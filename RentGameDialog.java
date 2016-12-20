package project4;

import javax.swing.*;

import project4.ValidDateMethods.VALID;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class RentGameDialog  extends JDialog implements ActionListener {
	
	private JTextField titleTxt;
	private JTextField renterTxt;
	private JTextField rentedOnTxt;
	private JTextField DueBackTxt;
	private JComboBox rentTime;
	private String[] gameNames = {"Call Of Duty 3", "Halo 5", "Destiny", "TitanFall 2", "Battlefield 1", "Battlefield 4", "Dishonored"};
	private JComboBox games;
	private JComboBox playerJbox;
	private JButton okButton;
	private JButton cancelButton;
	private boolean closeStatus;
	Calendar c;
	Date date;
	SimpleDateFormat df;
	
	private Disk unit;

	/*********************************************************
		 Instantiate a Custom Dialog as 'modal' and wait for the
		 user to provide data and click on a button.

		 @param parent reference to the JFrame application
		 @param m an instantiated object to be filled with data
	 *********************************************************/

	public RentGameDialog(JFrame parent, Disk d) {
		
		// call parent and create a 'modal' dialog
		super(parent, true);
		
		//set calendar time
		c = Calendar.getInstance();
		date = Calendar.getInstance().getTime();
		c.setTime(date);
		df = new SimpleDateFormat("MM/dd/yyyy");
		
		setTitle("Rent a Game:");
		closeStatus = false;
		setSize(400,200);
		
		unit = d; 
		// prevent user from closing window
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		// instantiate and display text fields
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(8,2));
		
		textPanel.add(new JLabel("Your Name:"));
		renterTxt = new JTextField("John Doe",30);
		textPanel.add(renterTxt);
		
		textPanel.add(new JLabel("Title of Game:"));
		games = new JComboBox(gameNames);
		games.setBackground(Color.WHITE);
		textPanel.add(games);
		
		
//		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		textPanel.add(new JLabel("Rented on Date: "));
		rentedOnTxt = new JTextField(df.format(date),30);	
		rentedOnTxt.setEditable(true);
		rentedOnTxt.setBackground(Color.WHITE);
		textPanel.add(rentedOnTxt);
		
		String [] rentalOptions = {"1","3","7"};
		textPanel.add(new JLabel("Days rented: "));
		rentTime = new JComboBox(rentalOptions);
		rentTime.addActionListener(this);
		rentTime.setBackground(Color.WHITE);
		textPanel.add(rentTime);
		
		

//		switch((String)rentTime.getSelectedItem()){
//		case "1": 
//			c.add(Calendar.DATE, 1);  // number of days to add
////			date = c.getTime();
//			break;
//		case "3":
//			c.add(Calendar.DATE, 3);  // number of days to add
////			date = c.getTime();
//			break;
//		case "7":
//			c.add(Calendar.DATE, 7);  // number of days to add
//			break;
//			default: 
//				date = c.getTime();
//				DueBackTxt.setText("" + df.format(date));
//				break;
//		}
//		DueBackTxt.setText("" + df.format(date));
		
		
		
		
		textPanel.add(new JLabel("" + "Due Back: "));
		c.add(Calendar.DATE, 1);  // number of days to add
		date = c.getTime();
		DueBackTxt = new JTextField("" + df.format(date), 15);
		DueBackTxt.setEditable(true);
		DueBackTxt.setBackground(Color.WHITE);
		textPanel.add(DueBackTxt);
		
		textPanel.add(new JLabel("Console Type: "));
		playerJbox = new JComboBox(PlayerType.values());
		playerJbox.removeItem(PlayerType.DVD);
		playerJbox.setBackground(Color.WHITE);
		textPanel.add(playerJbox);
		
		getContentPane().add(textPanel, BorderLayout.CENTER);
		
		// Instantiate and display two buttons
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		setSize(300,300);
		setVisible (true);	
	}
	
	/**************************************************************
		 Respond to either button clicks
		 @param e the action event that was just fired
	 **************************************************************/
	public void actionPerformed(ActionEvent e) {
		c = Calendar.getInstance();
		String s = (String)rentTime.getSelectedItem();
		if(rentTime == e.getSource()){
			switch(s){
			case "1": 
				c.add(Calendar.DATE, 1);  // number of days to add
				date = c.getTime();
				break;
			case "3":
				c.add(Calendar.DATE, 3);  // number of days to add
				date = c.getTime();
				break;
			case "7":
				c.add(Calendar.DATE, 7);  // number of days to add
				date = c.getTime();
				break;
				default: 
					date = c.getTime();
					break;
			}

			DueBackTxt.setText("" + df.format(date));
			
		}
		
//		JButton button = (JButton) e.getSource();
		else if(e.getSource() == cancelButton){
			closeStatus = false;
			dispose();
		}
		// if OK clicked the fill the object
		else if (e.getSource() == okButton) {
			// save the information in the object
			closeStatus = true;
			
//			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date daterentedOn, dateDue;
			try {
				if(!VALID.isValidDateString(rentedOnTxt.getText()) ||
						!VALID.isValidDateString(DueBackTxt.getText()))
				throw new IllegalArgumentException();

				daterentedOn = df.parse(rentedOnTxt.getText());
				dateDue = df.parse(DueBackTxt.getText());
				
				GregorianCalendar rentedOn = new GregorianCalendar();
				GregorianCalendar dueBackOn = new GregorianCalendar();

				rentedOn.setTime(daterentedOn);
				dueBackOn.setTime(dateDue);
				
				unit.setRentedOn(rentedOn);
				unit.setDueBack(dueBackOn);
				unit.setNameOfRenter(renterTxt.getText());
				unit.setTitle((String)games.getSelectedItem());
				unit.setPlayer((PlayerType)playerJbox.getSelectedItem());
				dispose();
				
			} 
			catch(IllegalArgumentException e2){
				JOptionPane.showMessageDialog(this, "Please Enter Valid Date in MM/DD/YYYY Format");
			}
			catch (ParseException e1) {
				System.out.println ("I have unepectly quit, sorry! goodbye");
			}
//			dispose();
		}
		
		// make the dialog disappear
//		dispose();
	}
	
	/**************************************************************
		 Return a String to let the caller know which button
		 was clicked
		 
		 @return an int representing the option OK or CANCEL
	 **************************************************************/
	public boolean closeOK(){
		return closeStatus;
	}
	public boolean closeCanc(){
		return closeStatus;
	}
}
