import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Main {
    File file = new File("./Records.txt");
    ArrayList <Airlines> record;
    Scanner in;
    
    JFrame frame;
    JLabel text;
    
    void program() {
        load();
        
        frame = new JFrame("Airloka [PROTOTYPE]");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        JPanel panel = new JPanel();
        JButton add = new JButton("Add");
        JButton delete = new JButton("Delete");
        JButton edit = new JButton("Edit");
        
        add.addActionListener((e) -> add());
        delete.addActionListener((e) -> remove());
        edit.addActionListener((e) -> edit());
        
        text = new JLabel();
        display();
        JScrollPane pane = new JScrollPane(text);
        panel.add(add);
        panel.add(delete);
        panel.add(edit);
        frame.add(panel, BorderLayout.SOUTH);
        frame.add(pane, BorderLayout.CENTER);
        
        frame.setVisible(true);
        
        write();
        
        in.close();
    }
    
    void load() {
        try {
            in = new Scanner(file);
        
            record = new ArrayList<Airlines>(0);
        
            while (in.hasNext()) {
                int type = in.nextInt();
                in.nextLine();
                String name = in.nextLine();
                int rating = in.nextInt();
            
                switch (type) {
                    case 0:
                        double xp = in.nextDouble();
                        record.add(new RewardAirlines(name, rating, xp)); break;
                    case 1:
                        int tier = in.nextInt();
                        record.add(new TierAirlines(name, rating, tier)); break;
                }
            }
        
            in.close();
        } catch (Exception err) {
            System.out.println("Error reading file");
            System.exit(-1);
        }
    }
    
    void write() {
        try {
            FileWriter out = new FileWriter(file);
        
            String text = "";
        
            for (int i = 0; i != record.size(); i++) {
                text += record.get(i).getType() + "\n";
                text += record.get(i).getName() + "\n";
                text += record.get(i).getRating() + "\n";
                if (record.get(i).getType() == 0) text += ((RewardAirlines)record.get(i)).getXP() + "\n";
                else if (record.get(i).getType() == 1) text += ((TierAirlines)record.get(i)).getTier() + "\n";
            }
        
            out.write(text);
            out.close();
        } catch (Exception err) {
            System.out.println("Error writing to file");
            System.exit(-2);
        }
    }
    
    void add() {
        try {
            String name = (String)JOptionPane.showInputDialog(frame,
            "Enter name of the Airline: ",
            "Add",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            null);
            
            int rating = Integer.parseInt((String)JOptionPane.showInputDialog(frame,
            "Enter rating of the Airline (0, 1, ...): ",
            "Add",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            null));
            
            int type = Integer.parseInt((String)JOptionPane.showInputDialog(frame,
            "Enter the type of Airline [0: Reward, 1: Tiered]: ",
            "Add",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            null));
            
            if (type == 1) {
                record.add(new TierAirlines(name, rating, 0));
            }
        
            else if (type == 0) {
                record.add(new RewardAirlines(name, rating, 0));
            }
            
            display();
            
            write();
        } catch (Exception err) {
            System.out.println("Wrong input" + err);
            System.exit(-3);
        }
    }
    
    void edit() {
        try {
            int id = Integer.parseInt((String)JOptionPane.showInputDialog(frame,
            "Enter index of the Airline to delete (0, 1, ...): ",
            "Delete",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            null));
            
            String newName = (String)JOptionPane.showInputDialog(frame,
            "Enter new name of the Airline: ",
            "Edit",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            null);
            
            int newRating = Integer.parseInt((String)JOptionPane.showInputDialog(frame,
            "Enter new rating of the Airline (0, 1, ...): ",
            "Edit",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            null));
            
            if (record.get(id).getType() == 1) {
                int newTier = Integer.parseInt((String)JOptionPane.showInputDialog(frame,
                "Enter new tier of the Tier Airline (0, 1, ...): ",
                "Edit",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null));
                ((TierAirlines)record.get(id)).setTier(newTier);
            }
        
            if (record.get(id).getType() == 0) {
                double newXP = Double.parseDouble((String)JOptionPane.showInputDialog(frame,
                "Enter new XP of the Reward Airline (0.0, 1.5, 4.5, ...): ",
                "Edit",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null));
                ((RewardAirlines)record.get(id)).setXP(newXP);
            }
            
            record.get(id).setName(newName);
            record.get(id).setRating(newRating);
            
            display();
            
            write();
        } catch (Exception err) {
            System.out.println("Wrong input" + err);
            System.exit(-3);
        }
    }
    
    void remove() {
        try {
            int id = Integer.parseInt((String)JOptionPane.showInputDialog(frame,
            "Enter index of the Airline to delete (0, 1, ...): ",
            "Delete",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            null));
        
            record.remove(id);
            
            display();
            
            write();
        } catch (Exception err) {
            System.out.println("Wrong input" + err);
            System.exit(-3);
        }
    }
    
    void display() {
        String str = "<html><p>";
        for (int i = 0; i != record.size(); i++) {
            str += (i + ": Name - " + record.get(i).getName() + " | Type - " + record.get(i).getType() + " | Rating - " + record.get(i).getRating()
                + ((record.get(i).getType() == 0) ? " | XP - " + ((RewardAirlines)record.get(i)).getXP() : " | Tier - " + ((TierAirlines)record.get(i)).getTier()) + "<br/>");
        }
        
        str += "</p></html>";
        text.setText(str);
    }
    
    public static void main(String[] args) {
        new Main().program();
    }
}
