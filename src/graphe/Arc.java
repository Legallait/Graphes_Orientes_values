package graphe;

public class Arc {
    private final String source;
    private final String destination;
    private final int valeur;

    public Arc(String source, String destination, int valeur){
        if (valeur < 0)
            throw new IllegalArgumentException("valeur negative");
        this.source = source;
        this.destination = destination;
        this.valeur = valeur;
    }

    public Arc(String source){
        this.source = source;
        this.destination = "";
        this.valeur = 0;
    }

    public String getSource(){
        return source;
    }

    public String getDestination(){
        return destination;
    }

    public int getValuation(){
        return valeur;
    }

    public String toString(){
        if(destination.length() != 0)
            return source + "-" + destination + "(" + valeur + ")";
        else
            return source + ":";
    }

}
