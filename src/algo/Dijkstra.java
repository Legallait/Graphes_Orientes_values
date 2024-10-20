package algo;
import graphe.IGrapheConst;
import java.util.ArrayList;
import java.util.Map;

public class Dijkstra {

    public static void dijkstra(IGrapheConst graphe, String debut, Map<String, Integer> distances, Map<String, String> predecesseur) {
        for (String sommet : graphe.getSommets()) {
            distances.put(sommet, Integer.MAX_VALUE);
            predecesseur.put(sommet, null);
        }
        distances.put(debut, 0);

        ArrayList<String> sommetsAVisiter = new ArrayList<>();
        sommetsAVisiter.add(debut);

        while (!sommetsAVisiter.isEmpty()) {
            String sommetCourant = sommetsAVisiter.remove(0);
            for (String voisin : graphe.getSucc(sommetCourant)) {
                int nouvelleDistance = distances.get(sommetCourant) + graphe.getValuation(sommetCourant, voisin);
                if (nouvelleDistance < distances.get(voisin)) {
                    distances.put(voisin, nouvelleDistance);
                    predecesseur.put(voisin, sommetCourant);
                    sommetsAVisiter.add(voisin);
                }
            }
        }
        distances.entrySet().forEach(entry -> {
            if (entry.getValue() == Integer.MAX_VALUE) {
                entry.setValue(-1);
            }
        });

        distances.entrySet().removeIf(entry -> entry.getValue() == -1);
    }
}