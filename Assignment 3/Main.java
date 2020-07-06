import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

class Main {
    File file = new File("./Records.txt");
    ArrayList <Airlines> record;
    Scanner in;
    
    void program() {
        System.out.println("This is a list of Airlines:");
        
        load();
        
        display();
        
        in = new Scanner(System.in);
        
        boolean edit = true;
        String next = "";
        
        while (edit) {
            System.out.print("\nModifying record (type /add, /edit, /delete, or /exit):");
            next = in.nextLine();
            
            switch (next) {
                case "/exit": edit = false;
                              break;
                case "/add": add();
                             break;
                case "/edit": edit();
                              break;
                case "/delete": remove();
                                break;
                default: break;
            }
            
            System.out.println();
            display();
        }
        
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
            System.out.print("Enter name of the Airline to add: ");
            String name = in.nextLine();
            System.out.print("Enter rating of the Airline to add: ");
            int rating = in.nextInt();
            System.out.print("Enter the type of Airline [0: Reward, 1: Tier]:");
            int type = in.nextInt();
            in.nextLine();
        
            switch (type) {
                case 0:
                    record.add(new RewardAirlines(name, rating)); break;
                case 1:
                    record.add(new TierAirlines(name, rating)); break;
            }
        } catch (Exception err) {
            System.out.println("Wrong input");
            System.exit(-3);
        }
    }
    
    void edit() {
        try {
            System.out.print("Enter index of the Airline to edit: ");
            int id = in.nextInt();
            in.nextLine();
        
            System.out.print("Enter new name of the Airline (" + record.get(id).getName() + "): ");
            String newName = in.nextLine();
            System.out.print("Enter new rating of the Airline (" + record.get(id).getRating() + "): ");
            int newRating = in.nextInt();
            in.nextLine();
        
            if (record.get(id).getType() == 1) {
                System.out.print("Enter new Tier of the Airline (" + ((TierAirlines)record.get(id)).getTier() + "): ");
                int newTier = in.nextInt();
                ((TierAirlines)record.get(id)).setTier(newTier);
            }
        
            if (record.get(id).getType() == 0) {
                System.out.print("Enter new XP of the Airline (" + ((RewardAirlines)record.get(id)).getXP() + "): ");
                double newXP = in.nextDouble();
                ((RewardAirlines)record.get(id)).setXP(newXP);
            }
        
            record.get(id).setName(newName);
            record.get(id).setRating(newRating);
        } catch (Exception err) {
            System.out.println("Wrong input");
            System.exit(-3);
        }
    }
    
    void remove() {
        try {
            System.out.print("Enter index of the Airline to delete: ");
            int id = in.nextInt();
            in.nextLine();
        
            record.remove(id);
        } catch (Exception err) {
            System.out.println("Wrong input");
            System.exit(-3);
        }
    }
    
    void display() {
        for (int i = 0; i != record.size(); i++) {
            System.out.println(i + ": Name - " + record.get(i).getName() + " | Type - " + record.get(i).getType() + " | Rating - " + record.get(i).getRating()
                + ((record.get(i).getType() == 0) ? " | XP - " + ((RewardAirlines)record.get(i)).getXP() : " | Tier - " + ((TierAirlines)record.get(i)).getTier()));
        }
    }
    
    public static void main(String[] args) {
        new Main().program();
    }
}
