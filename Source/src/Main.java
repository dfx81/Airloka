// Imports
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

// Main class:
// Used as the entry point of the program
// Implements ActionListener interface to listen to button events
// Setup the swing gui and read all required data from url
// Local file provided are the font file and the icon file only
class Main implements ActionListener{
	// Properties
	// Most Arrays are changed into ArrayList
    static Scanner in;
    private String source;
    private ArrayList <RewardAirlines> ra;
    private ArrayList <TierAirlines> ta;
    private ArrayList <FlexAirlines> fa;
    private JList<String> list;
    private ArrayList <String> dests;
    private ArrayList <String> locs;
    private ArrayList <String> type;
    private ArrayList <Integer> ids;
    private JFrame frame;
    
    // Constructor:
    // Get data from url to be used in program
    // Depending on internet speed, might load slowly
    // Different airlines of same categories stored as single array
    // NOTE: This is bad. Probably should cleanup so all type of
    //       airlines was stored in an Airlines array and casting
    //       to respective subclasses when needed (Polymorph)
    public Main(){
    	try {
    		// Read config file to get data source
    		in = new Scanner(new File("source.config"));
    		source = in.next();
    		String al = in.next();
    		String fl = in.next();

    		// Parse the contents of Airlines data
    		in = new Scanner(new URL(source + al).openStream());
    		in.nextLine();
    		int tot = in.nextInt();
    		in.nextLine();
    		in.nextLine();
    		in.nextLine();
    		// It's better to set initial size for ArrayList since the ArrayList
    		// resize is slow
    		int a = in.nextInt();
    		int b = in.nextInt();
    		int c = in.nextInt();
    		ra = new ArrayList <RewardAirlines>(a);
    		ta = new ArrayList <TierAirlines>(b);
    		fa = new ArrayList <FlexAirlines>(c);
    		in.nextLine();
    		in.nextLine();
    		in.nextLine();

    		// From there, initialize all the available airlines
    		for (int i = 0; i != tot; i++){
    			switch (in.next()){
    			    case "R": ra.add(in.nextInt(), new RewardAirlines(in.next().replace('_', ' '), in.nextInt(), 100, source + in.next(), source + in.next())); break;
    			    case "T": ta.add(in.nextInt(), new TierAirlines(in.next().replace('_', ' '), in.nextInt(), source + in.next(), source + in.next())); break;
    			    case "F": fa.add(in.nextInt(), new FlexAirlines(in.next().replace('_', ' '), in.nextInt(), source + in.next(), source + in.next())); break;
    			}
    		}

    		// Parse the flight list data
    		// Functions to see what flights available
    		in = new Scanner(new URL(source + fl).openStream());
    		in.nextLine();
    		tot = in.nextInt();
    		in.nextLine();
    		in.nextLine();
    		in.nextLine();

    		dests = new ArrayList <String>(tot);
    		locs = new ArrayList <String>(tot);
    		type = new ArrayList <String>(tot);
    		ids = new ArrayList <Integer>(tot);

    		for (int i = 0; i != tot; i++){
    			type.add(i, in.next());
    			ids.add(i, in.nextInt());
    			locs.add(i, in.next().replace('_', ' '));
    			dests.add(i, in.next().replace('_', ' '));
    		}

    		in.close();
    	} catch (Exception err) {
    		showError(err);
    	}
    }
    
    public static void main(String[] args) throws Exception{
        // Depending on OS, the look and feel will be seen like a native gui
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new Main().launch();
    }
    
    // The main gui codes
    // The gui will simply lists all of the flights available
    // Used BorderLayout as layout manager 
    public void launch(){
    	try {
    		// Use the provided font file
    		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/RobotoCondensed-Regular.ttf"));
    		font = font.deriveFont(16f);

    		frame = new JFrame("Airloka");
    		JLabel label = new JLabel("Select an airline: ");
    		JButton button = new JButton("Select");

    		label.setFont(font);
    		frame.setFont(font);
    		button.setFont(font);

    		String[] airlines = new String[ids.size()];

    		// Get details of every flight 
    		for (int i = 0; i != airlines.length; i++){
    			switch(type.get(i)){
    			    case "R": airlines[i] = ra.get(ids.get(i)).getText(locs.get(i), dests.get(i)); break;
    			    case "T": airlines[i] = ta.get(ids.get(i)).getText(locs.get(i), dests.get(i)); break;
    			    case "F": airlines[i] = fa.get(ids.get(i)).getText(locs.get(i), dests.get(i)); break;
    			}
    		}

    		// JScrollPane will overflow in case there's a lot of flights
    		// JList cannot accept ArrayList. There are workaround such as
    		// using toArray()
    		list = new JList<String>(airlines);
    		button.addActionListener(this);
    		JScrollPane panel = new JScrollPane(list);

    		panel.setFont(font);
    		list.setFont(font);

    		// Also set the icon to provided image
    		frame.setSize(480, 320);
    		frame.setIconImage(new ImageIcon("Assets/icon.png").getImage());
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frame.setLayout(new BorderLayout());
    		frame.setLocationRelativeTo(null);
    		frame.setResizable(false);
    		frame.add(label, BorderLayout.NORTH);
    		frame.add(panel, BorderLayout.CENTER);
    		frame.add(button, BorderLayout.SOUTH);
    		frame.setVisible(true);
    	} catch (Exception err) {
    		showError(err);
    	}
    }
    
    // MANDATORY OVERRIDE
    @Override
    public void actionPerformed(ActionEvent evt){
    	// Get the selection from the JList and call the showUI() method
    	// with the selection index as args
        showUI(list.getSelectedIndex());
    }
    
    // Used to determine whose gui to call
    // Check the type and use the correct Airline based on id
    // Then call the airline's gui method
    public void showUI(int select){
        System.out.println(locs.get(select) + " > " + dests.get(select));
        switch(type.get(select)){
            case "R": ra.get(ids.get(select)).showUI(frame, locs.get(select), dests.get(select)); break;
            case "T": ta.get(ids.get(select)).showUI(frame, locs.get(select), dests.get(select)); break;
            case "F": fa.get(ids.get(select)).showUI(frame, locs.get(select), dests.get(select)); break;
        }
    }
    
    // Updated the code to show error so the user won't have to wait
    // long to know the program crashed
    // All other classes/objects also had try catch block implemented
    // to catch any errors and shut the program
    public void showError(Exception err) {
    	err.printStackTrace();
    	JOptionPane.showMessageDialog(frame,
    			"An error has occured: \n" + err + "\n",
    	        "Error",
         	    JOptionPane.ERROR_MESSAGE);
    }
}