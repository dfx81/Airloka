class TierAirlines extends Airlines {
    private int tier = 0;
    
    public TierAirlines(String name, int rating) {
        super(name, rating, 1);
    }
    
    public TierAirlines(String name, int rating, int tier) {
        super(name, rating, 1);
        this.tier = tier;
    }
    
    public int getTier(){return tier;}
    public void setTier(int tier){
        this.tier = tier;
    }
}