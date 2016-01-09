package mentalPoker;

public enum Card {
    ACE (1),
    DEUX (2),
    SIX (3);

    public Integer values;   // in kilograms
    Card(Integer values) {
        this.values = values;
    }
    private Integer valeur() { return values; }
}