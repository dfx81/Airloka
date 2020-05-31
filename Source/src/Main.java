// Imports
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import java.net.URL;

// Main class:
// Used as the entry point of the program
// Implements ActionListener interface to listen to button events
// Setup the swing gui and read all required data from url
// Local file provided are the font file and the icon file only
class Main implements ActionListener{
	// Properties
    static Scanner in;
    private String source;
    private RewardAirlines[] ra;
    private TierAirlines[] ta;
    private FlexAirlines[] fa;
    private JList<String> list;
    private String[] dests;
    private String[] locs;
    private String[] type;
    private int[] ids;
    private JFrame frame;
    
    // Constructor:
    // Get data from url to be used in program
    // Depending on internet speed, might load slowly
    // Different airlines of same categories stored as single array
    public Main() throws Exception{
    	// Read config file to get data source
    	in = new Scanner(new File("source.config"));
    	source = in.next();
    	
    	// Parse the contents of Airlines data
        in = new Scanner(new URL(source + "AirlinesList.txt").openStream());
        in.nextLine();
        int tot = in.nextInt();
        in.nextLine();
        in.nextLine();
        in.nextLine();
        ra = new RewardAirlines[in.nextInt()];
        ta = new TierAirlines[in.nextInt()];
        fa = new FlexAirlines[in.nextInt()];
        in.nextLine();
        in.nextLine();
        in.nextLine();
        
        // From there, initialize all the available airlines
        for (int i = 0; i != tot; i++){
            switch (in.next()){
                case "R": ra[in.nextInt()] = new RewardAirlines(in.next().replace('_', ' '), in.nextInt(), 100, source + in.next(), source + in.next()); break;
                case "T": ta[in.nextInt()] = new TierAirlines(in.next().replace('_', ' '), in.nextInt(), source + in.next(), source + in.next()); break;
                case "F": fa[in.nextInt()] = new FlexAirlines(in.next().replace('_', ' '), in.nextInt(), source + in.next(), source + in.next()); break;
            }
        }
        
        // Parse the flight list data
        // Functions to see what flights available
        in = new Scanner(new URL(source + "FlightList.txt").openStream());
        in.nextLine();
        tot = in.nextInt();
        in.nextLine();
        in.nextLine();
        in.nextLine();
        
        dests = new String[tot];
        locs = new String[tot];
        type = new String[tot];
        ids = new int[tot];
        
        for (int i = 0; i != tot; i++){
            type[i] = in.next();
            ids[i] = in.nextInt();
            locs[i] = in.next().replace('_', ' ');
            dests[i] = in.next().replace('_', ' ');
        }
        
        in.close();
    }
    
    public static void main(String[] args) throws Exception{
    	// Depending on OS, the look and feel will be seen like a native gui
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new Main().launch();
    }
    
    // The main gui codes
    // The gui will simply lists all of the flights available
    // Used BorderLayout as layout manager 
    public void launch() throws Exception{
    	// Use the provided font file
        Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/RobotoCondensed-Regular.ttf"));
        font = font.deriveFont(16f);
        
        frame = new JFrame("Airloka");
        JLabel label = new JLabel("Select an airline: ");
        JButton button = new JButton("Select");
        
        label.setFont(font);
        frame.setFont(font);
        button.setFont(font);
        
        String[] airlines = new String[ids.length];
        
        // Get details of every flight 
        for (int i = 0; i != airlines.length; i++){
            switch(type[i]){
                case "R": airlines[i] = ra[ids[i]].getText(locs[i], dests[i]); break;
                case "T": airlines[i] = ta[ids[i]].getText(locs[i], dests[i]); break;
                case "F": airlines[i] = fa[ids[i]].getText(locs[i], dests[i]); break;
            }
        }
        
        // JScrollPane will overflow in case there's a lot of flights
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
        System.out.println(locs[select] + " > " + dests[select]);
        switch(type[select]){
            case "R": ra[ids[select]].showUI(frame, locs[select], dests[select]); break;
            case "T": ta[ids[select]].showUI(frame, locs[select], dests[select]); break;
            case "F": fa[ids[select]].showUI(frame, locs[select], dests[select]); break;
        }
    }
}