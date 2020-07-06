import java.util.Scanner;

// Parent class
class Airlines{
    private String name;
    private int rating;
    private int type;
    
    public Airlines(String name, int rating) {
       this.name = name;
       this.rating = rating;
    }
    
    public Airlines(String name, int rating, int type) {
       this.name = name;
       this.rating = rating;
       this.type = type;
    }
    
    // Standard getters & setters
    public String getName(){return name;}
    public int getRating(){return rating;}
    public int getType(){return type;}
    
    public void setName(String name){this.name = name;}
    public void setRating(int rating){this.rating = rating;}
}