// Imports
import java.net.URL;
import java.util.Scanner;
import javax.swing.*;

// Child class that uses reward system
// The pricing model is similar to parent so it uses its parent version of getPrice()
class RewardAirlines extends Airlines{
    private double tier;
    private String[] rewardTiers;
    
    // Constructor version with fresh reward progress (0xp)
    public RewardAirlines(String name, int rating, String url, String rewardURL) throws Exception{
       super(name, rating, url);
       loadReward(rewardURL);
    }
    
    // Constructor version to use if we want to set the reward progress
    public RewardAirlines(String name, int rating, double xp, String url, String rewardURL) throws Exception{
       super(name, rating, url);
       loadReward(rewardURL);
       tier = xp;
    }
    
    // Load some extra data required by this airline
    // Data fetched from url
    public void loadReward(String url){
        try {
        	URL data = new URL(url);
            Scanner in = new Scanner(data.openStream());
            
            rewardTiers = new String[Integer.parseInt(in.next())];
            
            for (int i = 0; i != rewardTiers.length; i++){
                rewardTiers[i] = in.nextLine();
            }
            
            in.close();
        } catch (Exception err) {
        	err.printStackTrace();
        	System.out.println("ERROR: " + err);
        	System.exit(-1);
        }
    }
    
    // Reschedule flight method
    // Check if eligible and do the required calculations
    public void rescheduleFlight(JFrame frame){
        for (int i = 0; i != rewardTiers.length; i++){
            if (rewardTiers[i].contains("RF")){
                double targetTier = Double.parseDouble(rewardTiers[i].substring(rewardTiers[i].indexOf(';') + 1));
                
                if (tier >= targetTier){
                    tier -= targetTier;
                    
                    // RESCHEDULE CODE
                    System.out.println("RESCHEDULED TO NEXT FLIGHT");
                    JOptionPane.showMessageDialog(frame,
                    "You can now board the next flight on schedule\n"
                    +   "Current Miles : " + tier + "xp");
                    break;
                }
                
                else{
                    System.out.println("NOT ELIGIBLE");
                    JOptionPane.showMessageDialog(frame,
                    "Not eligible for flight rescheduling\n"
                    +   "Current Miles : " + tier + "xp\n"
                    +   "Required      : " + targetTier + "xp");
                    break;
                }
            }
        }
    }
    
    // Similar to rescheduleFlight() method
    public void earlyBoarding(JFrame frame){
        for (int i = 0; i != rewardTiers.length; i++){
            if (rewardTiers[i].contains("EB")){
                double targetTier = Double.parseDouble(rewardTiers[i].substring(rewardTiers[i].indexOf(';') + 1));
                
                if (tier >= targetTier){
                    tier -= targetTier;
                    
                    // EARLY BOARDING CODE
                    System.out.println("YOU CAN BOARD THE PLANE 15MIN EARLY");
                    JOptionPane.showMessageDialog(frame,
                        "You can now board the plane 15 mins early\n"
                    +   "Current Miles : " + tier + "xp");
                    break;
                }
                
                else{
                    System.out.println("NOT ELIGIBLE");
                    JOptionPane.showMessageDialog(frame,
                    "Not eligible for early boarding\n"
                    +   "Current Miles : " + tier + "xp\n"
                    +   "Required      : " + targetTier + "xp");
                    break;
                }
            }
        }
    }
    
    // Getters + Setters
    public double getTier(){return tier;}
    public void setTier(double tier){this.tier = tier;}
    public String[] getRewardList(){return rewardTiers;}
    
    // Override the method in parent
    @Override
    public String toString(){
        return super.toString() + "\n"
        + "MILES  : " + tier;
    }
    
    // The bulk of this airline's gui code
    // Uses JOptionPane to prevent focus from being changed
    // from this gui (Dialog also works)
    public void showUI(JFrame frame, String loc, String des){
        int early = JOptionPane.showConfirmDialog(frame,
            "Do you want to board the plane 15 mins early?\n"
            + "Current Miles: " + tier + "xp",
            loc + " > " + des,
            JOptionPane.YES_NO_OPTION);
            
        if (early == JOptionPane.CLOSED_OPTION) return;
        
        int change = JOptionPane.showConfirmDialog(frame,
            "Do you want to reschedule your flight?\n"
            + "Current Miles: " + tier + "xp",
            loc + " > " + des,
            JOptionPane.YES_NO_OPTION);
            
        if (change == JOptionPane.CLOSED_OPTION) return;
        
        int confirm = JOptionPane.showConfirmDialog(frame,
            "Is this correct?\n"
            + "Early Boarding : " + (early == JOptionPane.YES_OPTION) + "\n"
            + "Reschedule Flight : " + (change == JOptionPane.YES_OPTION) + "\n\n"
            + "Current Miles : " + tier + "xp",
            loc + " > " + des,
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION){
            if (early == JOptionPane.YES_OPTION) earlyBoarding(frame);
            if (change == JOptionPane.YES_OPTION) rescheduleFlight(frame);
            
            super.showUI(frame, loc, des);
        }
    }
}