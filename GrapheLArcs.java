package graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrapheLArcs implements IGraphe {
    private final List<Arc> arcs;

    public GrapheLArcs (String chaine){
        this.arcs = new ArrayList<>();
        this.peupler(chaine);
    }

    public GrapheLArcs (){
        arcs = new ArrayList<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud))
            arcs.add(new Arc(noeud));
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination))
            throw new IllegalArgumentException("arc deja present");
        arcs.add(new Arc(source, destination, valeur));
    }

    @Override
    public void oterSommet(String noeud) {
        for (int i = 0; i < arcs.size(); i++)
            if(arcs.get(i).getSource().equals(noeud) || arcs.get(i).getDestination().equals(noeud)){
                arcs.remove(i);
                i--;
            }
    }

    @Override
    public void oterArc(String source, String destination) {
        if(!contientArc(source, destination))
            throw new IllegalArgumentException("arc inexistant");
        for (int i = 0; i < arcs.size(); i++)
            if(arcs.get(i).getSource().equals(source) && arcs.get(i).getDestination().equals(destination)){
                arcs.remove(i);
                i--;
            }
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<>();
        for(Arc arc: arcs){
            String source = arc.getSource();
            String destination = arc.getDestination();
            if(!sommets.contains(source))
                sommets.add(source);
            if(!sommets.contains(destination) && destination.length() != 0)
                sommets.add(destination);
        }
        Collections.sort(sommets);
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<>();
        for(Arc arc: arcs)
            if(arc.getSource().equals(sommet) && arc.getDestination().length() != 0)
                if (!successeurs.contains(arc.getDestination()))
                    successeurs.add(arc.getDestination());
        return successeurs;
    }

    @Override
    public int getValuation(String src, String dest) {
        for(Arc arc: arcs)
            if(arc.getSource().equals(src) && dest.equals(arc.getDestination()))
                return arc.getValuation();
        return 0;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return getSommets().contains(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for(Arc arc: arcs)
            if(arc.getSource().equals(src) && dest.equals(arc.getDestination()))
                return true;
        return false;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < arcs.size() - 1; i++)
            s.append(arcs.get(i)).append(", ");
        s.append(arcs.get(arcs.size()-1));
        return s.toString();
    }
}
