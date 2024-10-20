package graphe;

public class Arc {
    private String source;
    private String destination;
    private int valeur;

    public Arc(String source, String destination, int valeur){
        this.source = source;
        this.destination = destination;
        this.valeur = valeur;
    }

    public String getSource(){
        return source;
    }

    public String getDestination(){
        return destination;
    }

    public int getValeur(){
        return valeur;
    }

    public String toString(){
        if(destination.length() != 0)
            return source + "-" + destination + "(" + valeur + ")";
        else
            return source + ":";
    }

}
