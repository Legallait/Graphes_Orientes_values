package graphe.types;

import graphe.IGraphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheHHAdj implements IGraphe {
    private final Map<String, Map<String, Integer>> hhadj;

    public GrapheHHAdj(String chaine) {
        hhadj = new HashMap<>();
        this.peupler(chaine);
    }

    public GrapheHHAdj() {
        hhadj = new HashMap<>();

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
        if(!contientSommet(source)){
            ajouterSommet(source);
        }
        if(!contientSommet(destination)){
            ajouterSommet(destination);
        }
        hhadj.get(source).put(destination, valeur);
    }
    @Override
    public void oterSommet(String noeud) {
        if(contientSommet(noeud)){
            hhadj.remove(noeud);
            for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
                for (Map.Entry<String, Integer> arc : entry.getValue().entrySet()) {
                    if(arc.getKey().equals(noeud)){
                        entry.getValue().remove(arc);
                    }
                }
            }
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if(!contientArc(source, destination))
            throw new IllegalArgumentException("arc inexistant");
        if(contientArc(source, destination)){
            hhadj.get(source).remove(destination);
        }
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
        List<String> succ = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : hhadj.get(sommet).entrySet()) {
            succ.add(entry.getKey());
        }
        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        return hhadj.get(src).get(dest);
    }

    @Override
    public boolean contientSommet(String sommet) {
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            if(entry.getKey().equals(sommet)){
                return true;
            }
        }return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (contientSommet(src) && contientSommet(dest)){
            for (Map.Entry<String, Integer> entry : hhadj.get(src).entrySet()) {
                if(entry.getKey().equals(dest)){
                    return true;
                }
            }
        }return false;
    }

    public String toString() {
        return toAString();
    }
}
