// Imports
import java.net.URL;
import java.util.Scanner;
import javax.swing.*;

class TierAirlines extends Airlines{
    // Enum for various seat classes (private since only this class uses it)
    private enum Tier{
        BUDGET,
        ECONOMY,
        BUSINESS,
        FIRST_CLASS
    }
    
    // Properties
    private Tier tier = Tier.BUDGET;
    private String[] tierFeatures;
    
    // Constructor
    public TierAirlines(String name, int rating, String url, String tierURL) throws Exception{
        super(name, rating, url);
        loadTiers(tierURL);
    }
    
    // Fetch extra data from url
    public void loadTiers(String url) throws Exception{
        URL data = new URL(url);
        Scanner in = new Scanner(data.openStream());
        
        tierFeatures = new String[in.nextInt()];
        in.nextLine();
        
        for (int i = 0; in.hasNext(); i++){
            tierFeatures[i] = in.nextLine();
        }
        
        in.close();
    }
    
    // Getters + Setters
    public Tier getTier(){return tier;}
    public void setTier(String type){
        switch (type){
            case "BUDGET": tier = Tier.BUDGET; break;
            case "ECONOMY": tier = Tier.ECONOMY; break;
            case "BUSINESS": tier = Tier.BUSINESS; break;
            case "FIRST_CLASS": tier = Tier.FIRST_CLASS; break;
            default : tier = Tier.BUDGET;
        }
    }
    public String[] getTierFeatures(){return tierFeatures;}
    
    // Override getPrice method to account for different pricing model 
    @Override
    public double getPrice(String loc, String des){
        for (int i = 0; i != super.getPricing().length; i += 5){
            if (super.getPricing()[i].indexOf(loc) < super.getPricing()[i].indexOf(des)){
                return Double.parseDouble(super.getPricing()[(i + 1) + tier.ordinal()]);
            }
        }
        
        return -1;
    }
    
    public String toString(){
        return super.toString() + "\n"
        + "TIER   : " + tier.name();
    }
    
    // Returns flight detail for main ui
    public String getText(String loc, String des){
        return super.getName() + " (Rating : " + super.getRating() + " / 5) - " + loc + " > " + des;
    }
    
    // The airline's gui code
    // Uses JOptionPane to always preserve focus
    // Dialog can be used to if needed
    public void showUI(JFrame frame, String loc, String des){
        Object[] types = {Tier.BUDGET.name(), Tier.ECONOMY.name(), Tier.BUSINESS.name(), Tier.FIRST_CLASS.name()};
        String select = (String)JOptionPane.showInputDialog(frame,
            "Select your seat tier : ",
            loc + " > " + des,
            JOptionPane.PLAIN_MESSAGE,
            null,
            types,
            tier.name());
        
        if (select == null) return;
        
        setTier(select);
        
        JOptionPane.showMessageDialog(frame,
            "Ticket Price : " + String.format("RM%.2f\n", this.getPrice(loc, des))
        +   "Seat Tier : " + tier.name(),
            loc + " > " + des,
            JOptionPane.PLAIN_MESSAGE);
    }
}