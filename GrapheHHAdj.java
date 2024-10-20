package graphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheHHAdj implements IGraphe{
    private final Map<String, Map<String, Integer>> hhadj;

    public GrapheHHAdj() {
    this.hhadj = new HashMap<>();
    }

    public GrapheHHAdj(String chaine) {
        this.hhadj = new HashMap<>();
        this.peupler(chaine);
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud))
            hhadj.put(noeud, new HashMap<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (valeur < 0)
            throw new IllegalArgumentException("valeur negative");
        if (contientArc(source, destination))
            throw new IllegalArgumentException("arc deja existant");
        ajouterSommet(source);
        ajouterSommet(destination);
        hhadj.get(source).put(destination, valeur);
    }

    @Override
    public void oterSommet(String noeud) {
        if(contientSommet(noeud))
            hhadj.remove(noeud);
    }

    @Override
    public void oterArc(String source, String destination) {
        if (contientArc(source, destination))
            hhadj.get(source).remove(destination);
        else throw new IllegalArgumentException("arc inexistant");
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            sommets.add(entry.getKey());
        }
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        return new ArrayList<>(hhadj.get(sommet).keySet());
    }

    @Override
    public int getValuation(String src, String dest) {
        return hhadj.get(src).get(dest);
    }

    @Override
    public boolean contientSommet(String sommet) {
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet())
            if(entry.getKey().equals(sommet))
                return true;
        return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        return contientSommet(src) && hhadj.get(src).containsKey(dest);
    }


    @Override
    public String toString() {
        ArrayList<String> arcsString = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            if (entry.getValue().isEmpty())
                arcsString.add(entry.getKey() + ":");
            else
                for (Map.Entry<String, Integer> arc : entry.getValue().entrySet())
                    arcsString.add(entry.getKey() + "-" + arc.getKey() + "(" + arc.getValue() + ")");
        }
        arcsString.sort(String::compareTo);
        return arcsString.toString().substring(1, arcsString.toString().length() - 1);
    }
}