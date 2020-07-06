class RewardAirlines extends Airlines{
    private double xp = 0;
    
    public RewardAirlines(String name, int rating) {
       super(name, rating, 0);
    }
    
    public RewardAirlines(String name, int rating, double xp) {
       super(name, rating, 0);
       this.xp = xp;
    }
    
    public double getXP() {return xp;}
    public void setXP(double xp) {this.xp = xp;}
}