package graphe.types;

import graphe.Arc;
import graphe.IGraphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheLAdj implements IGraphe {
    private final Map<String, List<Arc>> ladj;

    public GrapheLAdj(String chaine){
        ladj = new HashMap<>();
        this.peupler(chaine);
    }

    public GrapheLAdj(){
        ladj = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud))
            ladj.put(noeud, new ArrayList<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination))
            throw new IllegalArgumentException("arc deja existant");
        if(!contientSommet(source)){
            ajouterSommet(source);
        }
        if(!contientSommet(destination)){
            ajouterSommet(destination);
        }
        ladj.get(source).add(new Arc(source, destination, valeur));
    }

    @Override
    public void oterSommet(String noeud) {
        if(contientSommet(noeud)){
            ladj.remove(noeud);
            for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
                for (Arc arc : entry.getValue()) {
                    if(arc.getDestination().equals(noeud)){
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
        for (Arc arc : ladj.get(source)) {
            if(arc.getDestination().equals(destination)){
                ladj.get(source).remove(arc);
            }
        }
    }

    @Override
    public List<String> getSommets() {
        ArrayList<String> sommets = new ArrayList<>();
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            sommets.add(entry.getKey());
        }
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<>();
        for (Arc arc : ladj.get(sommet)) {
            successeurs.add(arc.getDestination());
        }
        return successeurs;
    }

    @Override
    public int getValuation(String src, String dest) {
        for (Arc arc : ladj.get(src)) {
            if(arc.getDestination().equals(dest)){
                return arc.getValuation();
            }
        }
        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            if(entry.getKey().equals(sommet))
                return true;
        }return false;
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if(contientSommet(src)){
            for (Arc arc : ladj.get(src)) {
                if(arc.getDestination().equals(dest)){
                    return true;
                }
            }
        }
        return false;
    }

    public String toString(){
        return toAString();
    }
}
