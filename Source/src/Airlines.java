// Imports
import java.net.URL;
import java.util.Scanner;
import javax.swing.*;

// Parent class
class Airlines{
    private String name;
    private int rating;
    private String[] pricing;
    
    public Airlines(String name, int rating, String url) throws Exception{
       this.name = name;
       this.rating = rating;
       loadData(url);
    }
    
    // Load the pricing data
    public void loadData(String url) throws Exception{
        URL data = new URL(url);
        Scanner in = new Scanner(data.openStream());
        
        pricing = new String[in.nextInt()];
        in.nextLine();
        
        for (int i = 0; in.hasNext(); i++){
            pricing[i] = in.nextLine();
        }
        
        in.close();
    }
    
    // Standard getters
    public String getName(){return name;}
    public int getRating(){return rating;}
    
    // Get price will lookup the data fetched earlier and grab the appropriate price
    public double getPrice(String loc, String des){
        for (int i = 0; i != pricing.length; i++){
            if (pricing[i].indexOf(loc) < pricing[i].indexOf(des)){
                return Double.parseDouble(pricing[i].substring(pricing[i].indexOf(';') + 1));
            }
        }
        
        return -1;
    }
    
    // Get the whole pricing data
    public String[] getPricing(){return pricing;}
    
    // Print appropriate data
    public String toString(){
        return "NAME   : " + name + "\n"
        + "RATING : " + rating;
    }
    
    public String getText(String loc, String des){
        return name + " (Rating : " + rating + " / 5) - " + loc + " > " + des;
    }
    
    // Basic gui code
    // Simply prints ticket price
    public void showUI(JFrame frame, String loc, String des){
        JOptionPane.showMessageDialog(frame,
            "Ticket Price  : " + String.format("RM%.2f", getPrice(loc, des)),
            loc + " > " + des,
            JOptionPane.PLAIN_MESSAGE);
    }
}