// Imports
import java.net.URL;
import java.util.Scanner;
import javax.swing.*;

class FlexAirlines extends Airlines{
    // Properties
    private double[] extraPricing;
    private boolean privateTV = false;
    private boolean powerSocket = false;
    private boolean recliningSeat = false;
    private boolean wifi = false;
    
    // Constructor
    public FlexAirlines(String name, int rating, String url, String extraURL) throws Exception{
       super(name, rating, url);
       loadExtraFee(extraURL);
    }
    
    // Load extra data required for the airline
    // Data fetched from url
    public void loadExtraFee(String url){
    	try {
    		URL data = new URL(url);
            Scanner in = new Scanner(data.openStream());
            
            extraPricing = new double[in.nextInt()];
            
            for (int i = 0; i != extraPricing.length; i++){
                extraPricing[i] = in.nextDouble();
            }
            
            in.close();
    	} catch (Exception err) {
    		err.printStackTrace();
    		System.out.println("ERROR: " + err);
    		System.exit(-1);
    	}
    }
    
    // Getters + Setters
    public boolean getTV(){return privateTV;}
    public void setTV(boolean b){privateTV = b;}
    public boolean getSocket(){return powerSocket;}
    public void setSocket(boolean b){powerSocket = b;}
    public boolean getSeat(){return recliningSeat;}
    public void setSeat(boolean b){recliningSeat = b;}
    public boolean getWifi(){return wifi;}
    public void setWifi(boolean b){wifi = b;}
    
    // Modified getPrice because of different pricing model
    public double getPrice(String loc, String des){
        double fee = super.getPrice(loc, des);
        
        if (privateTV) fee += extraPricing[0];
        if (powerSocket) fee += extraPricing[1];
        if (recliningSeat) fee += extraPricing[2];
        if (wifi) fee += extraPricing[3];
        
        return fee;
    }
    
    public String toString(){
        return super.toString() + "\n"
        + "TV     : (RM" + extraPricing[0] + ") " + privateTV + "\n"
        + "SOCKET : (RM" + extraPricing[1] + ") " + powerSocket + "\n"
        + "R.SEAT : (RM" + extraPricing[2] + ") " + recliningSeat + "\n"
        + "WIFI   : (RM" + extraPricing[3] + ") " + wifi;
    }
    
    // This airline's gui code
    // Uses JOptionPane to keep focus from changing
    // Dialog can also be used
    public void showUI(JFrame frame, String loc, String des){
        int tv = JOptionPane.showConfirmDialog(frame,
            "Do you want private TV?\n"
            + "Price : " + String.format("RM%.2f", extraPricing[0]),
            loc + " > " + des,
            JOptionPane.YES_NO_OPTION);
            
        if (tv == JOptionPane.CLOSED_OPTION) return;
            
        int socket = JOptionPane.showConfirmDialog(frame,
            "Do you want power socket?\n"
            + "Price : " + String.format("RM%.2f", extraPricing[1]),
            loc + " > " + des,
            JOptionPane.YES_NO_OPTION);
        
        if (socket == JOptionPane.CLOSED_OPTION) return;
         
        int seat = JOptionPane.showConfirmDialog(frame,
            "Do you want reclining seat?\n"
            + "Price : " + String.format("RM%.2f", extraPricing[2]),
            loc + " > " + des,
            JOptionPane.YES_NO_OPTION);
            
        if (seat == JOptionPane.CLOSED_OPTION) return;
        
        int itnet = JOptionPane.showConfirmDialog(frame,
            "Do you want wifi?\n"
            + "Price : " + String.format("RM%.2f", extraPricing[3]),
            loc + " > " + des,
            JOptionPane.YES_NO_OPTION);
        
        if (itnet == JOptionPane.CLOSED_OPTION) return;
        
        int confirm = JOptionPane.showConfirmDialog(frame,
            "Is this correct?\n"
            + "Private TV : " + String.format("RM%.2f\n", (tv == JOptionPane.YES_OPTION) ? extraPricing[0] : 0)
            + "Power Socket : " + String.format("RM%.2f\n", (socket == JOptionPane.YES_OPTION) ? extraPricing[1] : 0)
            + "Reclining Seat : " + String.format("RM%.2f\n", (seat == JOptionPane.YES_OPTION) ? extraPricing[2] : 0)
            + "Wifi : " + String.format("RM%.2f\n", (itnet == JOptionPane.YES_OPTION) ? extraPricing[3] : 0),
            loc + " > " + des,
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION){
            setTV(tv == JOptionPane.YES_OPTION);
            setSocket(socket == JOptionPane.YES_OPTION);
            setSeat(seat == JOptionPane.YES_OPTION);
            setWifi(itnet == JOptionPane.YES_OPTION);
            
            JOptionPane.showMessageDialog(frame,
                "Ticket Price  : " + String.format("RM%.2f", getPrice(loc, des)),
                loc + " > " + des,
                JOptionPane.PLAIN_MESSAGE);
        }
        else return;
    }
}